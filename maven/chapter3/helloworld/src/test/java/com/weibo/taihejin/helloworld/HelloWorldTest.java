package com.weibo.taihejin.helloworld;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HelloWorldTest {
  @Test
  public void testSayHello() {
    HelloWorld helloWorld = new HelloWorld();
    String result = helloWorld.sayHello();
    assertEquals("Hello world!", result);
  }
}
