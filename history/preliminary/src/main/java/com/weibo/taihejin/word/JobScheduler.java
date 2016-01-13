package com.weibo.taihejin.word;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by taihejin on 15-12-27.
 */
public class JobScheduler extends Configured implements Tool {

    private static final Logger LOG = LoggerFactory.getLogger(JobScheduler.class);

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        Path input = new Path("/data0/input_word");
        Path output = new Path("/data0/output_" + UUID.randomUUID());
        WordCount.run(conf, "wordCount", input, output);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new JobScheduler(), args);
    }
}

