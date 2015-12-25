package com.weibo.taihejin.diff.writable;

import com.weibo.taihejin.diff.SourceType;
import org.apache.hadoop.io.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by taihejin on 15-11-28.
 */

public class RecordKeyWritable implements Writable {

    private Text keyWritable = new Text();
    private ByteWritable typeWritable = new ByteWritable();

    public void write(DataOutput out) throws IOException {
        keyWritable.write(out);
        typeWritable.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        keyWritable.readFields(in);
        typeWritable.readFields(in);
    }


    public void setKeyWritable(Text key) {
        keyWritable.set(key);
    }

    public void setTypeWritable(SourceType type) {
        typeWritable.set((byte) type.ordinal());
    }

    public Text getKeyWritable() {
        return keyWritable;
    }

    public SourceType getTypeWritable() {
        return SourceType.values()[typeWritable.get()];
    }

}
