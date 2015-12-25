package com.weibo.taihejin.diff.old;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.weibo.taihejin.diff.SourceType;
import com.weibo.taihejin.diff.writable.RecordKeyWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by taihejin on 15-11-29.
 */
public class TemparetureConvertJob {

    private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();

    private static class TempareturePartitioner
            extends Partitioner<Text, RecordValueWritable> {
        @Override
        public int getPartition(Text key, RecordValueWritable value, int numPartitions) {
            return (key.hashCode() & 0x7FFFFFF) % numPartitions;
        }
    }


    private static class TemparetureGroupingComparator
            implements RawComparator<RecordKeyWritable> {
        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            Preconditions.checkArgument(l1 > 1);
            Preconditions.checkArgument(l2 > 1);
            return TEXT_COMPARATOR.compare(b1 ,s1 + 1, l1 - 1, b2, s2 + 1, l2 - 1);
        }

        public int compare(RecordKeyWritable key1, RecordKeyWritable key2) {
            return TEXT_COMPARATOR.compare(key1.getKeyWritable(), key2.getKeyWritable());
        }
    }

    private static class TemparetureSortingComparator implements RawComparator<RecordKeyWritable> {

        private static final TemparetureGroupingComparator GROUPING_COMPARATOR = new TemparetureGroupingComparator();

        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            int cmp = GROUPING_COMPARATOR.compare(b1, s1, l1, b2, s2, l2);
            if (cmp != 0) {
                return cmp;
            }
            return Ints.compare((int) b1[s1], (int) b2[s2]);
        }

        @Override
        public int compare(RecordKeyWritable o1, RecordKeyWritable o2) {
            int cmp = GROUPING_COMPARATOR.compare(o1, o2);
            if (cmp != 0) {
                return cmp;
            }
            return Ints.compare(o1.getTypeWritable().ordinal(), o2.getTypeWritable().ordinal());
        }
    }

    public static class ConvertMapper extends Mapper<Text, RecordValueWritable, Text, RecordValueWritable> {

        @Override
        public void map(Text key, RecordValueWritable value, Context context) throws IOException, InterruptedException {
            context.write(key, value);
        }

    }

    public static class ConvertReducer extends Reducer<Text, RecordValueWritable, NullWritable, Text> {

        @Override
        public void reduce(Text key, Iterable<RecordValueWritable> values, Context context) throws IOException, InterruptedException {
            Iterator<RecordValueWritable> iter = values.iterator();
            System.out.println("group ======= " + key.toString());
            while(iter.hasNext()) {
                RecordValueWritable value = iter.next();
                System.out.println((value.getTypeWritable() == SourceType.ONLINE ? " ONLINE\t" : " OFLINE\t") + value.getValueWritable().toString());
                context.write(NullWritable.get(), value.getValueWritable());
            }
        }
    }

    public static void run(
            Configuration conf,
            String jobName,
            Path onlineInputDir,
            Path offlineInputDir,
            Path outputDir) throws Exception {
        Job job = Job.getInstance(conf, jobName);
        job.setJobName(jobName);

        job.setJarByClass(TemparetureConvertJob.class);

        job.setMapperClass(ConvertMapper.class);
        job.setReducerClass(ConvertReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(RecordValueWritable.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(SequenceFileInputFormat.class);
        SequenceFileInputFormat.setInputPaths(job, onlineInputDir, offlineInputDir);

        TextOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setNumReduceTasks(1);

        job.setPartitionerClass(TempareturePartitioner.class);
        //job.setGroupingComparatorClass(TemparetureGroupingComparator.class);
        //job.setSortComparatorClass(TemparetureSortingComparator.class);

        job.waitForCompletion(true);
    }

}
