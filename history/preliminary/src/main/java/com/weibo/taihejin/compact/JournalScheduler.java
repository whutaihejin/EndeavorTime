package com.weibo.taihejin.compact;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.weibo.taihejin.compact.util.CompactUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by taihejin on 15-12-27.
 */
public class JournalScheduler extends Configured implements Tool {

    public  static final String DT_SEPARETOR = "-";

    private static final Logger LOG = LoggerFactory.getLogger(JournalScheduler.class);

    public interface CommandLineOptions {

        @Option(longName = "base-dir", defaultToNull = true)
        String baseDir();

        @Option(longName = "dt", defaultToNull = true)
        String dt();

        @Option(longName = "interval", defaultValue = "4")
        int interval();

    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        CommandLineOptions cli = CliFactory.parseArguments(CommandLineOptions.class, args);
        // 1. for base dir
        String baseDir = cli.baseDir();
        // check baseDir

        // 2. for dt
        String dt = cli.dt();
        // check dt
        // if check pass
        Path parentDir = new Path(baseDir);

        // 3. for interval
        int interval = cli.interval();
        // check interval

        // 4. generate input path
        LOG.info(" ====== wgre journal compact starts ...");
        ArrayList pathArray = Lists.newArrayList();
        for (int i = 0; i < interval; i++) {
            pathArray.add(new Path(parentDir, CompactUtils.getPath(dt, interval)));
        }
        //ImmutableList.of(new Path(sd, "*/*")),
        Path temp = new Path("/data0/dataset/journal_" + UUID.randomUUID());

        // stage1 convert
        Path output1 = new Path(temp, "stage1");
        JournalConvertJob.run(conf, "JournalConvert", ImmutableList.copyOf(pathArray), output1);

        // stage2 compact
        Path input2 = output1;
        Path output2 = new Path(temp, "stage2");
        JournalCompactJob.run(conf, "JournalCompact", input2, output2);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new JournalScheduler(), args);
    }
}

