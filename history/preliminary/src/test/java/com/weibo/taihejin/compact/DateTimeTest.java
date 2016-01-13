package com.weibo.taihejin.compact;

import com.weibo.taihejin.compact.util.CompactUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Random;

/**
 * Created by taihejin on 15-12-29.
 */
public class DateTimeTest {

    @Test
    public void testFormat() throws ParseException {
        String dt = "20151226-00";
        String next = CompactUtils.getToken(dt);
        Assert.assertEquals(next, "20151226-01");

        next = CompactUtils.getToken(dt, 1);
        Assert.assertEquals(next, "20151226-01");

        next = CompactUtils.getToken(dt, 6);
        Assert.assertEquals(next, "20151226-06");

        next = CompactUtils.getToken(dt, 24);
        Assert.assertEquals(next, "20151227-00");

        next = CompactUtils.getToken(dt, 48);
        Assert.assertEquals(next, "20151228-00");

        dt = "20151226-12";
        next = CompactUtils.getToken(dt, 1);
        Assert.assertEquals(next, "20151226-13");

        next = CompactUtils.getToken(dt, 0);
        Assert.assertEquals(next, "20151226-12");

    }

    @Test
    public void testFileName() {
        Random random = new Random();
        String.format("0x08", random.nextInt());
    }
}
