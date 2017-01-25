import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class test {
	public static void test01() throws Exception {
		File file = new File("./gensort100MB");
		File file2 = new File("./part-r-00000");
		Map<String, String> collec = new TreeMap<String, String>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				collec.put(line.substring(0, 9), line.substring(10));
			}
			Set<String> keySet = collec.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				System.out.println(key + "  " + collec.get(key));
				i++;
				if (i == 10)
					break;
			}

		}

		System.out.println();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file2))) {
			String line2;
			int i = 0;
			while ((line2 = bufferedReader.readLine()) != null) {
				// if(line.length()<100)
				// {
				// int j = 100 - line.length();
				// while(j!=0){
				// line = " " + line;
				// j--;
				// }
				// }

				System.out.println(line2);
				i++;
				if (i == 10)
					break;
			}
		}

	}

	public static void test02() {
		System.out.println("aa");
		System.out.println("a\na");

	}

	public static void test03() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("d", "ddddd");
		map.put("b", "bbbbb");
		map.put("a", "aaaaa");
		map.put("c", "ccccc");

		if (map.containsKey("a"))
			map.put("a", map.get("a") + "\na:aaabb");

		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + map.get(key));
		}

	}

	@SuppressWarnings("rawtypes")
	public static class tt implements Callable {
		Map<String, String> ma = new TreeMap<String, String>();

		@Override
		public Object call() throws Exception {
			// TODO Auto-generated method stub
			ma.put("a", "b");
			ma.put("b", "b");
			ma.put("c", "b");
			ma.put("d", "b");
			ma.put("e", "b");
			return ma;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void test04() throws InterruptedException, ExecutionException {
		Map[] mm = new Map[1];
		ExecutorService poolm = Executors.newFixedThreadPool(1);
		tt t = new tt();
		Future f = poolm.submit(t);
		f.get();
		poolm.shutdown();
		mm[0] = (Map<String, String>) f.get();
		Set<String> keySet = mm[0].keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + mm[0].get(key));
		}
	}

	public static void test05() {
	}

	@SuppressWarnings("resource")
	public static void test06() throws IOException {
		File file = new File("./part-r-00000");
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(200);
		String line;
		int i = 0;
		while ((line = raf.readLine()) != null) {
			System.out.println(line);
			i++;
			if (i == 10)
				break;
		}
	}

	public static void test07() {
		String a = "abad";
		String b = "abbd";
		int i = a.compareTo(b);
		System.out.println(i);

	}

	public static void test08() {
		String aString = "abcdefghijklmnop";
		System.out.println(aString.substring(0, 10));
		int x = 1;
		int y = 2;
		System.out.println(x / y);
	}

	// public static void test09() {
	// OutputCollector<String, Integer> outputCollector;
	// outputCollector.collect("abdc", 1);
	//
	// }
	@SuppressWarnings("resource")
	public static void test10() throws IOException {
		File f1 = new File("tmp01");
		FileWriter fos = new FileWriter(f1);
		PrintWriter pWriter = new PrintWriter(fos);
		pWriter.println("Hello");
		// f1.delete();

	}

	public static void test11() {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("ac");
		aList.add("E");
		aList.add("W");
		System.out.println(aList);
		Collections.sort(aList);
		System.out.println(aList);
	}

	public static void test12() {
		TreeMap<String, String> aMap = new TreeMap<>();
		System.out.println(aMap.size());
		aMap.put("Hello", "world");
		aMap.put("Hello2", "world");
		System.out.println(aMap.size());
		aMap.put("Hello1", "world");
		
	}
	public static void test13() {
		ArrayList<Integer> arrayList = new ArrayList<>();
		arrayList.add(0);
		arrayList.add(1);
		System.out.println(arrayList.get(0));
		arrayList.remove(0);
		arrayList.add(3);
		System.out.println(arrayList.get(1));
	}
	public static void test14() {
		Long iLong = 10L;
		UUID uuid = UUID.randomUUID();
		System.out.println(iLong.toString());
		System.out.println(uuid);
		
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		test14();
	}

}
