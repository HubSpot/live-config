package com.hubspot.liveconfig.resolver;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class DefaultPropertiesResolverTest {

  private static final Set<String> keySet = ImmutableSet.of("foo.bar.baz");
  private static final Map<String, String> map = ImmutableMap.of("foo.bar.baz", "true");

  private DefaultPropertiesResolver resolver;

  @Before
  public void setUp() {
    resolver = new DefaultPropertiesResolver("com.hubspot");
  }

  @Test
  public void testGet() {
    Assert.assertEquals("true", resolver.get("foo.bar.baz").get());
  }

  @Test
  public void testGetAbsent() {
    Assert.assertFalse(resolver.get("abc.xyz").isPresent());
  }

  @Test
  public void testKeySet() {
    Assert.assertEquals(keySet, resolver.keySet());
  }

}
