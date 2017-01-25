import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProjectionMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
	private Text word = new Text();
	private LongWritable count = new LongWritable();
	
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] split = value.toString().split("\t+");
		word.set(split[0]);
		if (split.length > 2) {
			try {
				count.set(Long.parseLong(split[2]));
				context.write(word, count);
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
		}
	}
}
