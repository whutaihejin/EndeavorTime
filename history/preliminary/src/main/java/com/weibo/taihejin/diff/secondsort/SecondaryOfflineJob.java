package com.weibo.taihejin.diff.secondsort;

import com.weibo.taihejin.diff.RecordValueWritable;
import com.weibo.taihejin.diff.SourceType;
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
 * Created by taihejin on 15-11-29.
 */
public class SecondaryOfflineJob {

    public static class TemparetureMapper extends Mapper<LongWritable, Text, RecordKeyWritable, Text> {

        private RecordKeyWritable keyWritable = new RecordKeyWritable();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            keyWritable.setTypeWritable(SourceType.OFFLINE);
        }

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            keyWritable.setKeyWritable(new Text(line.substring(0, 4)));
            context.write(keyWritable, value);
        }
    }

    public static void run(
            Configuration conf,
            String jobName,
            Path inputDir,
            Path outputDir) throws Exception {

        Job job = Job.getInstance(conf, jobName);
        job.setJarByClass(SecondaryOfflineJob.class);

        job.setMapperClass(TemparetureMapper.class);

        FileInputFormat.addInputPath(job, inputDir);

        SequenceFileOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setOutputKeyClass(RecordKeyWritable.class);
        job.setOutputValueClass(Text.class);

        job.setNumReduceTasks(0); // map-only job
        job.waitForCompletion(true);

    }
}
