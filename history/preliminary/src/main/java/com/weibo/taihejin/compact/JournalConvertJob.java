package com.weibo.taihejin.compact;

import com.weibo.taihejin.compact.io.JournalInputFormat;
import com.weibo.taihejin.compact.writable.RecordKeyWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import java.io.IOException;
import java.util.List;

/**
 * Created by taihejin on 15-12-27.
 */
public class JournalConvertJob {

    // Constant field for parse JSON Object to generate the Journal Key
    private static final String JSON_ID = "id";
    private static final String JSON_TYPE = "type";
    private static final String JSON_TIMESTAMP = "updatetime";

    public static class JournalMapper extends Mapper<NullWritable, BytesWritable, RecordKeyWritable, BytesWritable> {

        private RecordKeyWritable keyWritable = new RecordKeyWritable();

        private void generateKey(JSONObject json) {
            String stringKey = json.getString(JSON_ID) + '_' + json.getString(JSON_TYPE);
            long updatetime = json.getLong(JSON_TIMESTAMP);
            keyWritable.set(stringKey, updatetime);
        }

        @Override
        public void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
            JSONObject record = JSONObject.parseObject(new String(value.getBytes(), 0, value.getLength()));
            generateKey(record);
            context.write(keyWritable, value);
        }

    }

    public static void run(
            Configuration conf,
            String jobName,
            List<Path> inputDirs,
            Path outputDir) throws Exception {

        Job job = Job.getInstance(conf, jobName);
        job.setJarByClass(JournalConvertJob.class);

        job.setMapperClass(JournalMapper.class);
        job.setMapOutputKeyClass(RecordKeyWritable.class);
        job.setMapOutputValueClass(BytesWritable.class);

        JournalInputFormat.setInputPaths(job, inputDirs.toArray(new Path[inputDirs.size()]));
        job.setInputFormatClass(JournalInputFormat.class);
        // recursive read
        JournalInputFormat.setInputDirRecursive(job, true);

        SequenceFileOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setOutputKeyClass(RecordKeyWritable.class);
        job.setOutputValueClass(BytesWritable.class);

        job.setNumReduceTasks(0); // map-only job
        job.waitForCompletion(true);

    }

}
