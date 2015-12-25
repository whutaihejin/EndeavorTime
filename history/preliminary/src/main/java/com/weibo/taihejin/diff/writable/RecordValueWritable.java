package com.weibo.taihejin.diff.writable;

import com.weibo.taihejin.diff.SourceType;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by taihejin on 15-11-30.
 */
public class RecordValueWritable implements Writable {

    private ByteWritable typeWritable = new ByteWritable();
    private Text valueWritable = new Text();

    @Override
    public void write(DataOutput out) throws IOException {
        typeWritable.write(out);
        valueWritable.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        typeWritable.readFields(in);
        valueWritable.readFields(in);
    }

    public SourceType getTypeWritable() {
        return SourceType.values()[typeWritable.get()];
    }

    public void setTypeWritable(SourceType type) {
        typeWritable.set((byte) type.ordinal());
    }

    public Text getValueWritable() {
        return valueWritable;
    }

    public void setValueWritable(Text value) {
        valueWritable.set(value);
    }

    public void clear() {
        valueWritable.clear();
    }

    public void set(RecordValueWritable value) {
        setTypeWritable(value.getTypeWritable());
        setValueWritable(value.getValueWritable());
    }

    public boolean empty() {
        return valueWritable.toString().equals("");
    }

    @Override
    public String toString() {
        if (empty()) {
            return "NULL";
        }
        return (getTypeWritable() == SourceType.ONLINE ? "ONLINE\t" : "OFFLINE\t") + valueWritable;
    }
}
