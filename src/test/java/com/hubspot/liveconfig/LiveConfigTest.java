package com.hubspot.liveconfig;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class LiveConfigTest {

  private static final Map<String, String> properties = ImmutableMap.<String, String>builder()
      .put("mystring", "foobar")
      .put("myint", "123")
      .put("mylong", "1391558400000")
      .put("myfloat", "123.456")
      .put("mybool", "true")
      .put("myboolon", "on")
      .put("myboolfalse", "false")
      .put("mybooloff", "off")
      .put("mylist", "1,2,3,4,5")
      .build();

  private LiveConfig config;

  @Before
  public void setUp() {
    config = LiveConfig.fromMap(properties);
  }

  @Test
  public void testGetString() {
    Assert.assertEquals("foobar", config.getString("mystring"));
  }

  @Test
  public void testGetInt() {
    Assert.assertEquals(123, config.getInt("myint"));
  }

  @Test(expected = NumberFormatException.class)
  public void testGetIntInvalid() {
    config.getInt("mystring");
  }

  @Test
  public void testGetLong() {
    Assert.assertEquals(1391558400000L, config.getLong("mylong"));
  }

  @Test(expected = NumberFormatException.class)
  public void testGetLongInvalid() {
    config.getLong("mystring");
  }

  @Test
  public void testGetFloat() {
    Assert.assertEquals(123.456f, config.getFloat("myfloat"), 0.1);
  }

  @Test(expected = NumberFormatException.class)
  public void testGetFloatInvalid() {
    config.getFloat("mystring");
  }

  @Test
  public void testGetBoolean() {
    Assert.assertTrue(config.getBoolean("mybool"));
  }

  @Test
  public void testGetBooleanOn() {
    Assert.assertTrue(config.getBoolean("myboolon"));
  }

  @Test
  public void testGetBooleanFalse() {
    Assert.assertFalse(config.getBoolean("myboolfalse"));
  }

  @Test
  public void testGetBooleanOff() {
    Assert.assertFalse(config.getBoolean("mybooloff"));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetBooleanInvalid() {
    config.getBoolean("mystring");
  }

  @Test
  public void testGetList() {
    Assert.assertEquals(Arrays.asList("1", "2", "3", "4", "5"), config.getList("mylist"));
  }

  @Test
  public void testGetListInt() {
    Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5), config.getList("mylist", Integer::parseInt));
  }

}
