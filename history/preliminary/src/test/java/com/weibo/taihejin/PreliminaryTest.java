package com.weibo.taihejin;

import org.junit.Test;
import org.junit.Assert;
/**
 * Created by taihejin on 15-11-15.
 */
public class PreliminaryTest {
    @Test
    public void testSayHello() {
        Preliminary preliminary = new Preliminary();
        String result = preliminary.sayHello("taihejin");
        Assert.assertEquals(result, "hello taihejin");
    }
}
