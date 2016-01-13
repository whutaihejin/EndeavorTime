package com.weibo.taihejin.compact.io;


import com.google.protobuf.CodedOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by taihejin on 15-12-28.
 */
public class JournalOutputFormat<K, V> extends FileOutputFormat<K, V> {

    public JournalOutputFormat() { }

    public JournalRecordWriter<K, V> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        Configuration conf = job.getConfiguration();
        String extension = "";
        Path file = this.getDefaultWorkFile(job, extension);
        FileSystem fs = file.getFileSystem(conf);
        FSDataOutputStream fileOut = fs.create(file, false);
        return new JournalRecordWriter<K, V>(fileOut);
    }

    public static class JournalRecordWriter<K, V> extends RecordWriter<K, V> {

        private final DataOutputStream out;
        private final CodedOutputStream cout;


        public JournalRecordWriter(DataOutputStream out) {
            this.out = out;
            this.cout = CodedOutputStream.newInstance(out);
        }

        public void writeObject(Object o) throws IOException {
            if (o instanceof BytesWritable) {
                BytesWritable to = (BytesWritable)o;
                cout.writeRawVarint32(to.getLength());
                cout.writeRawBytes(to.getBytes(), 0, to.getLength());
            } else {
                this.out.write(o.toString().getBytes("UTF-8"));
            }
        }

        @Override
        public synchronized void write(K key, V value) throws IOException {
            boolean nullKey = key == null || key instanceof NullWritable;
            if (!nullKey) {
                throw new RuntimeException("null or NullWritable key is required!");
            }
            boolean nullValue = value == null || value instanceof NullWritable;
            if (!nullValue) {
                this.writeObject(value);
            }
        }

        @Override
        public synchronized void close(TaskAttemptContext context) throws IOException {
            this.out.close();
        }

    }


}


