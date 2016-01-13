package com.weibo.taihejin.compact.io;


import com.google.protobuf.CodedInputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by taihejin on 15-12-27.
 */
public class JournalRecordReader extends RecordReader<NullWritable, BytesWritable> {

    private DataInputStream in = null;
    private CodedInputStream cin = null;
    private NullWritable key = NullWritable.get();
    private BytesWritable value = new BytesWritable();

    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext)
            throws IOException, InterruptedException {
        FileSplit split = (FileSplit)inputSplit;
        Configuration conf = taskAttemptContext.getConfiguration();
        Path path = split.getPath();
        FileSystem fs = path.getFileSystem(conf);
        in = fs.open(path);
        cin = CodedInputStream.newInstance(in);
    }

    private byte[] readRecord() {
        try {
            int len = cin.readRawVarint32();
            return cin.readRawBytes(len);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        byte[] record = readRecord();
        if (record != null) {
            value = new BytesWritable(record);
            return true;
        }
        return false;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
