package com.hubspot.liveconfig.resolver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ChainedResolverTest {

  private static final Map<String, String> mapA = ImmutableMap.of("foo", "1");
  private static final Map<String, String> mapB = ImmutableMap.of("foo", "2", "bar", "2");
  private static final Map<String, String> mapC = ImmutableMap.of("foo", "3", "bar", "3", "baz", "3");

  private ChainedResolver resolver;

  @Before
  public void setUp() {
    List<Resolver> resolvers = ImmutableList.<Resolver>builder()
        .add(new ForwardingMapResolver(mapA))
        .add(new ForwardingMapResolver(mapB))
        .add(new ForwardingMapResolver(mapC))
        .build();
    resolver = new ChainedResolver(resolvers);
  }

  @Test
  public void testGet() {
    Assert.assertEquals("1", resolver.get("foo").get());
    Assert.assertEquals("2", resolver.get("bar").get());
    Assert.assertEquals("3", resolver.get("baz").get());
  }

  @Test
  public void testGetAbsent() {
    Assert.assertFalse("4", resolver.get("absent").isPresent());
  }

}
