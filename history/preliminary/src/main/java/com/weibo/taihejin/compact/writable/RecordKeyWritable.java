package com.weibo.taihejin.compact.writable;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by taihejin on 15-12-28.
 */
public class RecordKeyWritable implements Writable {

    private BytesWritable key = new BytesWritable();
    private LongWritable timestamp = new LongWritable();

    @Override
    public void write(DataOutput out) throws IOException {
        key.write(out);
        timestamp.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        key.readFields(in);
        timestamp.readFields(in);
    }

    public BytesWritable getKeyWritable() {
        return key;
    }

    public String getKeyString() {
        return new String(key.getBytes(), 0, key.getLength());
    }

    public LongWritable getTimestamp() {
        return timestamp;
    }

    public void set(byte[] key, long timestamp) {
        this.key.set(key, 0, key.length);
        this.timestamp.set(timestamp);
    }

    public void set(String key, long timestamp) {
        this.key.set(key.getBytes(), 0, key.length());
        this.timestamp.set(timestamp);
    }

    @Override
    public String toString() {
        return new String(key.getBytes(), 0, key.getLength()) + '_' + timestamp.get();
    }


}
