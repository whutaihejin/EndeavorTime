package com.weibo.taihejin.compact;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.weibo.taihejin.compact.io.JournalOutputFormat;
import com.weibo.taihejin.compact.writable.RecordKeyWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by taihejin on 15-12-28.
 */
public class JournalCompactJob {

    public static final short LONG_SIZE = 8;

    private static class JournalPartitioner
            extends Partitioner<RecordKeyWritable, BytesWritable> {
        @Override
        public int getPartition(RecordKeyWritable key, BytesWritable value, int numPartitions) {
            return (key.getKeyWritable().hashCode() & 0x7FFFFFF) % numPartitions;
        }
    }

    private static class JournalGroupingComparator implements RawComparator<RecordKeyWritable> {

        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            Preconditions.checkArgument(l1 > LONG_SIZE);
            Preconditions.checkArgument(l2 > LONG_SIZE);
            return BytesWritable.Comparator.compareBytes(b1, s1, l1 - LONG_SIZE, b2, s2, l2 - LONG_SIZE);
        }

        @Override
        public int compare(RecordKeyWritable key1, RecordKeyWritable key2) {
            return key1.getKeyWritable().compareTo(key2.getKeyWritable());
        }

    }

    private static class JournalSortingComparator implements RawComparator<RecordKeyWritable> {

        private static final JournalGroupingComparator
                GROUPING_COMPARATOR = new JournalGroupingComparator();

        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            Preconditions.checkArgument(l1 > LONG_SIZE);
            Preconditions.checkArgument(l2 > LONG_SIZE);
            int flag = GROUPING_COMPARATOR.compare(b1, s1, l1, b2, s2, l2);
            if (flag != 0) {
                return flag;
            }
            return -LongWritable.Comparator.compareBytes(b1, s1 + l1 - LONG_SIZE, LONG_SIZE, b2, s2 + l2 - LONG_SIZE, LONG_SIZE);
        }

        @Override
        public int compare(RecordKeyWritable key1, RecordKeyWritable key2) {
            int flag = GROUPING_COMPARATOR.compare(key1, key2);
            if (flag != 0) {
                return flag;
            }
            return -key1.getTimestamp().compareTo(key2.getTimestamp());
        }

    }

    public static class JournalMapper extends Mapper<RecordKeyWritable, BytesWritable, RecordKeyWritable, BytesWritable> {

        @Override
        protected void map(RecordKeyWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
            context.write(key, value);
        }
    }

    public static class JournalReducer extends Reducer<RecordKeyWritable, BytesWritable, NullWritable, BytesWritable> {

        private static int recordCounter = 0;
        private static int compactedCounter = 0;

        private String formatValue(JSONObject data) {
            StringBuilder sb = new StringBuilder();
            sb.append(data.getString("id") + '\t');
            sb.append(data.getString("type") + '\t');
            sb.append(data.getLong("updatetime") + '\t');
            return sb.toString();
        }

        @Override
        protected void reduce(RecordKeyWritable key, Iterable<BytesWritable> values, Context context) throws IOException, InterruptedException {
            compactedCounter++;
            Iterator<BytesWritable> iter = values.iterator();
            boolean flag = true;
            int statistic  = 0;
            while (iter.hasNext()) {
                recordCounter++;
                statistic++;
                BytesWritable value = iter.next();
                JSONObject data = JSONObject.parseObject(new String(value.getBytes(), 0, value.getLength()));
                System.out.printf("[value=%s]\n", formatValue(data));
                if (flag) {
                    context.write(NullWritable.get(), value);
                    flag = false;
                }
            }
            System.out.printf("=========%s\t%d\n", key, statistic);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
            System.out.printf("recordCounter=%s\ncompactedCounter=%s\ncompactedRate=%f\n", recordCounter, compactedCounter,
                    recordCounter == 0 ? 0 : compactedCounter * 1.0 / recordCounter);
        }
    }

    public static void run(Configuration conf,
                           String jobName,
                           Path inpuDir,
                           Path outputDir) throws IOException, InterruptedException, ClassNotFoundException {
        Job job = Job.getInstance(conf, jobName);
        job.setJobName(jobName);

        job.setJarByClass(JournalCompactJob.class);

        job.setMapperClass(JournalMapper.class);
        job.setReducerClass(JournalReducer.class);

        job.setMapOutputKeyClass(RecordKeyWritable.class);
        job.setMapOutputValueClass(BytesWritable.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(BytesWritable.class);

        job.setInputFormatClass(SequenceFileInputFormat.class);
        SequenceFileInputFormat.setInputPaths(job, inpuDir);

        JournalOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(JournalOutputFormat.class);
        job.setNumReduceTasks(1);

        job.setPartitionerClass(JournalPartitioner.class);
        job.setGroupingComparatorClass(JournalGroupingComparator.class);
        job.setSortComparatorClass(JournalSortingComparator.class);

        job.waitForCompletion(true);
    }
}
