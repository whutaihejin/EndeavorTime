package com.weibo.taihejin.explore;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.net.URI;

/**
 * Created by taihejin on 15-12-25.
 */
public class FileSystemDoubleCat {

    public static void main(String[] args) throws Exception {
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        FSDataInputStream in = null;
        try {
            in = fs.open(new Path(uri));
            IOUtils.copyBytes(in, System.out, 1024 * 4, false);
            in.seek(0); // go back to the start of the file
            IOUtils.copyBytes(in, System.out, 1024 * 4, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }
}
