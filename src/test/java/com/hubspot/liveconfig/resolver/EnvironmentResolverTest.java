package com.hubspot.liveconfig.resolver;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class EnvironmentResolverTest {

  public EnvironmentResolver resolver;

  @Before
  public void setUp() {
    Map<String, String> envVars = ImmutableMap.<String, String>builder()
        .put("BUILD_INFO", "some build info string...")
        .build();
    resolver = new EnvironmentResolver(envVars);
  }

  @Test
  public void testGet() {
    Assert.assertEquals("some build info string...", resolver.get("build.info").get());
  }

  @Test
  public void testKeySet() {
    Assert.assertEquals(Collections.singleton("build.info"), resolver.keySet());
  }

}
