import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Maper implements Callable<TreeMap<String, String>> {
	TreeMap<String,String> collection = new TreeMap<String, String>();
	RandomAccessFile file;
	long startPosition;
	long size;
	public Maper(File file, long l, long size) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		this.file = new RandomAccessFile(file, "rw");
		this.startPosition = l;
		this.size = size/100;
	}
	
	
	@Override
	public TreeMap<String, String> call() throws Exception {
		// TODO Auto-generated method stub
		String line;
		this.file.seek(startPosition);
		System.out.println(startPosition);
		long i = 0;
		while (i < size) {
			line = this.file.readLine();
//			if(i == 0)
//				System.out.println(line);
			if(line != null)
				collection.put(line.substring(0,10), line.substring(10));
			i ++;
		}
		return collection;
	}
}

class Reducer  {
	@SuppressWarnings("unchecked")
	TreeMap<String, String>[] collections = new TreeMap[1];
	TreeMap<String, String> result;
	public Reducer(TreeMap<String, String>[] coll) {
		// TODO Auto-generated constructor stub
		this.collections = coll;
		this.result = coll[0];
	}
	
	public TreeMap<String, String> run() {
		for(int i = 1; i<collections.length; i++){
			Set<String> keySet = collections[i].keySet();
		    Iterator<String> iter = keySet.iterator();
		    while (iter.hasNext()) {
		    	String key = iter.next();
//		    	System.out.println(key + "  " + collections[i].get(key));
		    	if(result.containsKey(key))
		    	{
		    		String tmp = result.get(key)+"\n"+ key +collections[i].get(key);
		    		collections[i].put(key, tmp);
		    	}
		    	result.put(key, collections[i].get(key));
		    }
		}
		
		return result;
	}
	
	
}

public class terasort
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
		
		Long start = System.currentTimeMillis();
		File input = new File(args[1]);
		File output = new File(args[2]+"/result");
		int num = Integer.parseInt(args[0]);
		TreeMap<String, String>[] mappedCollections = new TreeMap[num];
		TreeMap<String, String> result;
		
//		File input = new File("./gensort100MB");
//		File output = new File("./result");
		long size = input.length();
//		System.out.println(size);
		ExecutorService poolm = Executors.newFixedThreadPool(num);
		
		Maper[] mapers = new Maper[num];
		Future<TreeMap<String, String>>[] f = new Future[num];
		for(int t =0;t<num;t++)
		{
			mapers[t] = new Maper(input, size/num * t, size/num);
//			System.out.println(size/num *t + "   " + size/num);
			f[t] = poolm.submit(mapers[t]);
		}
		for(int t = 0; t<num; t ++)
			mappedCollections[t] = f[t].get();
		
		poolm.shutdown();

		Reducer reducer = new Reducer(mappedCollections);
		
		result = reducer.run();
		
		FileWriter fos = new FileWriter(output);
		PrintWriter pWriter = new PrintWriter(fos);
		Set<String> keySet = result.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String tmp = key + result.get(key);
            pWriter.println(tmp);
        }
        Long end = System.currentTimeMillis();
        Long duration = end - start;
        System.out.println("Duration: " + duration);
		fos.close();
	}
}