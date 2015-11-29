package com.weibo.taihejin.diff;

import com.weibo.taihejin.diff.secondsort.SecondaryOfflineJob;
import com.weibo.taihejin.diff.secondsort.SecondaryOnlineJob;
import com.weibo.taihejin.diff.secondsort.SecondarySortingJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by taihejin on 15-11-29.
 */
public class JobScheduler extends Configured implements Tool {

    private static final Logger LOG = LoggerFactory.getLogger(JobScheduler.class);

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        // <online_input_path> <offline_input_path> <output_path>

        Path tmp = new Path("/data0/" + UUID.randomUUID());

        // stage 0
        Path input0 = new Path("/data0/input_online");
        Path output0 = new Path(tmp, "output0");
        //TemparetureOnlineJob.run(conf, "online", input0, output0);
        SecondaryOnlineJob.run(conf, "online", input0, output0);

        // stage 1
        Path input1 = new Path("/data0/input_offline");
        Path output1 = new Path(tmp, "output1");
        //TemparetureOfflineJob.run(conf, "offline", input1, output1);
        SecondaryOfflineJob.run(conf, "offline", input1, output1);

        // stage 2
        Path output2 = new Path(tmp, "output2");
        //TemparetureConvertJob.run(conf, "diff", output0, output1, output2);
        SecondarySortingJob.run(conf, "diff", output0, output1, output2);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new JobScheduler(), args);
    }
}
