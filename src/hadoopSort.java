import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class hadoopSort {
	public static class myMapper extends Mapper<Object, Text, Text, Text> {
		private Text word = new Text();
		private Text val = new Text();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			word.set(line.substring(0,10));
			val.set(line.substring(10));
			context.write(word, val);

		}
	}



	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: terasort <in> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "Hadoop Sort");
		job.setJarByClass(hadoopSort.class);
		job.setMapperClass(myMapper.class);
		job.setCombinerClass(Reducer.class);
		job.setReducerClass(Reducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(4);
		
		Path input = new Path(otherArgs[0]);
		Path output = new Path(otherArgs[1]);
		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);
		
		FileSystem fSystem = FileSystem.get(conf);
		if(fSystem.exists(output))
			fSystem.delete(output, true);
		
		long size = fSystem.getContentSummary(input).getSpaceConsumed();
		int signal = job.waitForCompletion(true)? 0 : 1;
		
		long end = System.currentTimeMillis();
		long duration = (end - startTime)/1000;
		System.out.println("Time: " + Long.toString(duration) + "s");
		float throughput = size/(1000000*duration);
		System.out.println("Sort throughput: " + throughput + "MB/s");
		System.exit(signal);
	}

}
