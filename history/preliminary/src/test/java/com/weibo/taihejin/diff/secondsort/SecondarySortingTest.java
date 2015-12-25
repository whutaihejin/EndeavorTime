package com.weibo.taihejin.diff.secondsort;

import com.weibo.taihejin.diff.JobScheduler;
import org.apache.hadoop.mapred.JobConf;
import org.junit.Test;

/**
 * Created by taihejin on 15-11-30.
 */
public class SecondarySortingTest {

    @Test
    public void diffTest() throws Exception {
        JobConf conf = new JobConf();
        conf.set("fs.default.name", "file:///");
        conf.set("mapred.job.tracker", "local");

        JobScheduler scheduler = new JobScheduler();
        scheduler.setConf(conf);
        scheduler.run(null);
    }
}
