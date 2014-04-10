package com.hubspot.liveconfig;

import com.google.common.collect.Maps;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.hubspot.liveconfig.value.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InjectionTest {

  private static final String PROPERTY_KEY = "foo.bar.baz";

  private Injector injector;
  private LiveConfig liveConfig;
  private final Map<String, String> properties = Maps.newHashMap();

  @Before
  public void setUp() {
    liveConfig = LiveConfig.fromMap(properties);
    injector = Guice.createInjector(new LiveConfigModule(liveConfig));
  }

  @After
  public void tearDown() {
    properties.clear();
  }

  private static class InjectString {
    public final String val;

    @Inject
    public InjectString(@Named(PROPERTY_KEY) String val) {
      this.val = val;
    }
  }

  private static class InjectInt {
    public final int val;

    @Inject
    public InjectInt(@Named(PROPERTY_KEY) int val) {
      this.val = val;
    }
  }

  private static class InjectStringValue {
    public final Value<String> val;

    @Inject
    public InjectStringValue(@Named(PROPERTY_KEY) Value<String> val) {
      this.val = val;
    }
  }

  private static class InjectIntValue {
    public final Value<Integer> val;

    @Inject
    public InjectIntValue(@Named(PROPERTY_KEY) Value<Integer> val) {
      this.val = val;
    }
  }

  @Test(expected = ConfigurationException.class)
  public void testStringNotBound() {
    injector.getInstance(InjectString.class);
  }

  @Test(expected = ConfigurationException.class)
  public void testIntNotBound() {
    injector.getInstance(InjectInt.class);
  }

  @Test(expected = ConfigurationException.class)
  public void testStringValueNotBound() {
    injector.getInstance(InjectStringValue.class);
  }

  @Test(expected = ConfigurationException.class)
  public void testIntValueNotBound() {
    injector.getInstance(InjectIntValue.class);
  }

  @Test
  public void testStringBound() {
    setProperty("abc");
    InjectString injected = injector.getInstance(InjectString.class);
    assertEquals("abc", injected.val);
  }

  @Test
  public void testIntBound() {
    setProperty("123");
    InjectInt injected = injector.getInstance(InjectInt.class);
    assertEquals(123, injected.val);
  }

  @Test
  public void testStringValueBound() {
    setProperty("abc");
    InjectStringValue injected = injector.getInstance(InjectStringValue.class);
    assertEquals("abc", injected.val.get());
  }

  @Test
  public void testIntValueBound() {
    setProperty("123");
    InjectIntValue injected = injector.getInstance(InjectIntValue.class);
    assertEquals(123, injected.val.get().intValue());
  }

  @Test(expected = ConfigurationException.class)
  public void testIntUnparsable() {
    setProperty("abc");
    injector.getInstance(InjectInt.class);
  }

  @Test
  public void testIntValueUnparsable() {
    setProperty("abc");
    Value<Integer> val = injector.getInstance(InjectIntValue.class).val;
    try {
      val.get();
      fail("should've thrown exception");
    } catch (NumberFormatException e) {
    }
  }

  private void setProperty(String value) {
    properties.put(PROPERTY_KEY, value);
    setUp();
  }

}
