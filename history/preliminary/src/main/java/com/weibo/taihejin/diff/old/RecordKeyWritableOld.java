package com.weibo.taihejin.diff.old;

import com.weibo.taihejin.diff.SourceType;
import org.apache.hadoop.io.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by taihejin on 15-11-28.
 */

public class RecordKeyWritableOld extends WritableComparator implements Writable {

    private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
    private static final IntWritable.Comparator INT_COMPARATOR = new IntWritable.Comparator();


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

    public int compare(RecordKeyWritableOld x, RecordKeyWritableOld y) {
        int flag = TEXT_COMPARATOR.compare(x.getKeyWritable(), y.getKeyWritable());
        if (flag != 0) {
            return flag;
        }
        return INT_COMPARATOR.compare(x.getTypeWritable().ordinal(), y.getTypeWritable().ordinal());
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
