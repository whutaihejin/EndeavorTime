package com.weibo.taihejin.diff;

import com.weibo.taihejin.diff.writable.RecordKeyWritable;
import com.weibo.taihejin.diff.writable.RecordValueWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * Created by taihejin on 15-11-28.
 */
public class ConvertHFileJob {


    public static class TemparetureMapper extends Mapper<LongWritable, Text, RecordKeyWritable, RecordValueWritable> {

        private RecordKeyWritable keyWritable = new RecordKeyWritable();
        private RecordValueWritable valueWritable = new RecordValueWritable();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            keyWritable.setTypeWritable(SourceType.ONLINE);
            valueWritable.setTypeWritable(SourceType.ONLINE);
        }

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            keyWritable.setKeyWritable(new Text(line.substring(0, 4)));
            valueWritable.setValueWritable(value);
            context.write(keyWritable, valueWritable);
        }

    }

    public static void run(
            Configuration conf,
            String jobName,
            Path inputDir,
            Path outpurDir) throws Exception {

        Job job = Job.getInstance(conf, jobName);
        job.setJarByClass(TemparetureMapper.class);

        job.setMapperClass(TemparetureMapper.class);

        job.setOutputKeyClass(RecordKeyWritable.class);
        job.setOutputValueClass(RecordValueWritable.class);

        FileInputFormat.addInputPath(job, inputDir);
        SequenceFileOutputFormat.setOutputPath(job, outpurDir);
        job.setNumReduceTasks(0); // map-only job
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.waitForCompletion(true);
    }

}
