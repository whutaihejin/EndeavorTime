package com.weibo.taihejin.diff;

import com.google.common.base.Preconditions;
import com.weibo.taihejin.diff.writable.RecordKeyWritable;
import com.weibo.taihejin.diff.writable.RecordValueWritable;
import com.weibo.taihejin.diff.old.TemparetureConvertJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by taihejin on 15-11-29.
 */
public class ConvertJob {

    private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
    private static final IntWritable.Comparator INT_COMPARATOR = new IntWritable.Comparator();

    private static class TempareturePartitioner
            extends Partitioner<RecordKeyWritable, RecordValueWritable> {
        @Override
        public int getPartition(RecordKeyWritable key, RecordValueWritable value, int numPartitions) {
            return (key.getKeyWritable().hashCode() & 0x7FFFFFF) % numPartitions;
        }
    }

    private static class TemparetureGroupingComparator implements RawComparator<RecordKeyWritable> {

        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            Preconditions.checkArgument(l1 > 1);
            Preconditions.checkArgument(l2 > 1);
            return TEXT_COMPARATOR.compare(b1, s1, l1 - 1, b2, s2, l2 - 1);
        }

        @Override
        public int compare(RecordKeyWritable key1, RecordKeyWritable key2) {
            return TEXT_COMPARATOR.compare(key1.getKeyWritable(), key2.getKeyWritable());
        }

    }



    private static class TemparetureSortingComparator implements RawComparator<RecordKeyWritable> {

        private static final TemparetureGroupingComparator
                GROUPING_COMPARATOR = new TemparetureGroupingComparator();

        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            Preconditions.checkArgument(l1 > 1);
            Preconditions.checkArgument(l2 > 1);
            int flag = GROUPING_COMPARATOR.compare(b1, s1, l1, b2, s2, l2);
            if (flag != 0) {
                return flag;
            }
            return INT_COMPARATOR.compare(b1, l1 - 1, 1, b2, l2 - 1, 1);
        }

        @Override
        public int compare(RecordKeyWritable key1, RecordKeyWritable key2) {
            int flag = GROUPING_COMPARATOR.compare(key1, key2);
            if (flag != 0) {
                return flag;
            }
            return INT_COMPARATOR.compare(key1.getTypeWritable().ordinal(), key2.getTypeWritable().ordinal());
        }

    }

    public static class ConvertMapper extends Mapper<RecordKeyWritable, RecordValueWritable, RecordKeyWritable, RecordValueWritable> {

        @Override
        public void map(RecordKeyWritable key, RecordValueWritable value, Context context) throws IOException, InterruptedException {
            context.write(key, value);
        }

    }

    public static class ConvertReducer extends Reducer<RecordKeyWritable, RecordValueWritable, NullWritable, Text> {

        // four types of output
        private static final String FILE_NAME_ONLINE_NULL = "ONLINE_NULL";
        private static final String FILE_NAME_NULL_OFFLINE = "NULL_OFFLINE";
        private static final String FILE_NAME_VALUE_EQUAL = "VALUE_EQUAL";
        private static final String FILE_NAME_VALUE_DIFF_ONLINE = "VALUE_DIFF_ONLINE";
        private static final String FILE_NAME_VALUE_DIFF_OFFLINE = "VALUE_DIFF_OFFLINE";

        private static MultipleOutputs<NullWritable, Text> multipleOutputs;

        private RecordValueWritable onlineValue = new RecordValueWritable();
        private RecordValueWritable offlineValue = new RecordValueWritable();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            multipleOutputs = new MultipleOutputs<NullWritable, Text>(context);
        }

        @Override
        public void reduce(RecordKeyWritable key, Iterable<RecordValueWritable> values, Context context) throws IOException, InterruptedException {
            Iterator<RecordValueWritable> iter = values.iterator();

            // we could assert that there is at least one element int the iter,
            // but the first one may be the online one or the offline one. To
            // naming the variable with consistency we assume the first one is
            // online value, the next is offline value if there's one only for
            // naming. we hope clarify your possible confusion with the comment.
            onlineValue.clear();
            offlineValue.clear();
            onlineValue.set(iter.next());
            if (iter.hasNext()) {
                offlineValue.set(iter.next());
            }
            System.out.println("online = " + onlineValue);
            System.out.println("offline = " + offlineValue);
            if (onlineValue.getTypeWritable() == SourceType.ONLINE) { // online value
                if (!offlineValue.empty()) { // offline has value
                    if (onlineValue.getValueWritable().equals(offlineValue.getValueWritable())) {
                        multipleOutputs.write(NullWritable.get(), onlineValue.getValueWritable(), FILE_NAME_VALUE_EQUAL);
                    } else {
                        multipleOutputs.write(NullWritable.get(), onlineValue.getValueWritable(), FILE_NAME_VALUE_DIFF_ONLINE);
                        multipleOutputs.write(NullWritable.get(), offlineValue.getValueWritable(), FILE_NAME_VALUE_DIFF_OFFLINE);
                    }
                } else {
                    multipleOutputs.write(NullWritable.get(), onlineValue.getValueWritable(), FILE_NAME_ONLINE_NULL);
                }
            } else { // no online value
                multipleOutputs.write(NullWritable.get(), onlineValue.getValueWritable(), FILE_NAME_NULL_OFFLINE);
            }
            // may change the Granularity in the future by parameter.
            /*
            if (onlineValue.equals(offlineValue)) {
                multipleOutputs.write(NullWritable.get(), onlineValue, FILE_NAME_VALUE_EQUAL);
            } else {
                multipleOutputs.write(NullWritable.get(), onlineValue, FILE_NAME_VALUE_DIFF);
            }*/

        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
            multipleOutputs.close();
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

        job.setMapOutputKeyClass(RecordKeyWritable.class);
        job.setMapOutputValueClass(RecordValueWritable.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(SequenceFileInputFormat.class);
        SequenceFileInputFormat.setInputPaths(job, onlineInputDir, offlineInputDir);

        TextOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setNumReduceTasks(1);

        job.setPartitionerClass(TempareturePartitioner.class);
        job.setGroupingComparatorClass(TemparetureGroupingComparator.class);
        job.setSortComparatorClass(TemparetureSortingComparator.class);

        job.waitForCompletion(true);
    }

}
