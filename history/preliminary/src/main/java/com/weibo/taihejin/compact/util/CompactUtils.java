package com.weibo.taihejin.compact.util;

import org.apache.hadoop.fs.Path;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by taihejin on 15-12-29.
 */
public class CompactUtils {

    public  static final String DT_SEPARETOR = "-";

    public static final String DT_FORMAT = "yyyyMMdd-HH";
    public static final long HOUR_INTERVAL = 1 * 60 * 60 * 1000;

    public static String getToken(String current) throws ParseException {
       return getToken(current, 1);
    }

    public static String getToken(String current, int interval) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DT_FORMAT);
        Date date = formatter.parse(current);
        long value = date.getTime();
        value += HOUR_INTERVAL * interval;
        return formatter.format(new Date(value));
    }

    public static Path getPath(String dt) throws ParseException {
        return getPath(dt, 0);
    }

    public static Path getPath(String dt, int interval) throws ParseException {
        String token = getToken(dt, interval);
        String[] splited = token.split(DT_SEPARETOR);
        String dateString = splited[0];
        String hourString = splited[1];
        return new Path(dateString, hourString);
    }

}
