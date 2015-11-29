package com.weibo.taihejin.diff;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public final class FileSystemUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileSystemUtils.class);

    private FileSystemUtils() {}

    /** 将匹配的文件移动至目标目录，文件名不变 */
    public static void copyFilesToDir(Configuration conf, Path pattern, Path outputDir) throws IOException {
        moveFilesToDirAddingPrefix(conf, pattern, outputDir, "");
    }

    public static void moveFilesToDirAddingPrefix(
            Configuration conf, Path pattern , Path outputDir, String prefix) throws IOException {
        FileSystem fs = pattern.getFileSystem(conf);
        FileStatus[] statuses = fs.globStatus(pattern);
        if (statuses == null) {
            LOG.info("No match for pattern " + pattern);
            return;
        }
        for (FileStatus status : statuses) {
            Path f = status.getPath();
            doRename(fs, f, new Path(outputDir, prefix + f.getName()));
        }
    }

    public static void moveFile(Configuration conf, Path pattern, Path outPath) throws IOException {
        FileSystem fs = pattern.getFileSystem(conf);
        FileStatus[] statuses = fs.globStatus(pattern);
        if (statuses == null || statuses.length == 0) {
            LOG.info("No match for pattern " + pattern);
            return;
        }
        if (statuses.length > 1) {
            List<Path> matches = Lists.newArrayList();
            for (FileStatus status : statuses) {
                matches.add(status.getPath());
            }
            throw new IOException("Multiple files are matched by " + pattern + ": " + Joiner.on(", ").join(matches));
        }
        doRename(fs, statuses[0].getPath(), outPath);
    }

    private static void doRename(FileSystem fs, Path src, Path dst) throws IOException {
        fs.mkdirs(dst.getParent());
        fs.copyFromLocalFile(src, dst);
        LOG.info("Moved " + src + " to " + dst);
    }
}
