package com.hubspot.liveconfig;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.hubspot.liveconfig.value.Value;

import java.util.Map;
import java.util.Set;

public class LiveConfigModule extends AbstractModule {
  private static final TypeLiteral<Value<String>> STRING_VALUE_CLASS = new TypeLiteral<Value<String>>() {
  };
  private static final TypeLiteral<Value<Integer>> INTEGER_VALUE_CLASS = new TypeLiteral<Value<Integer>>() {
  };
  private static final TypeLiteral<Value<Long>> LONG_VALUE_CLASS = new TypeLiteral<Value<Long>>() {
  };
  private static final TypeLiteral<Value<Float>> FLOAT_VALUE_CLASS = new TypeLiteral<Value<Float>>() {
  };
  private static final TypeLiteral<Value<Double>> DOUBLE_VALUE_CLASS = new TypeLiteral<Value<Double>>() {
  };
  private static final TypeLiteral<Value<Boolean>> BOOLEAN_VALUE_CLASS = new TypeLiteral<Value<Boolean>>() {
  };

  private final LiveConfig config;

  public LiveConfigModule(LiveConfig config) {
    this.config = config;
  }

  @Override
  protected void configure() {
    bind(LiveConfig.class).toInstance(config);

    Map<String, String> map = config.asMap();
    Names.bindProperties(binder(), map);
    bindNamedValues(binder(), map.keySet());
  }

  private void bindNamedValues(Binder binder, Set<String> propertyNames) {
    binder = binder.skipSources(Names.class);
    for (String key : propertyNames) {
      binder.bind(Key.get(STRING_VALUE_CLASS, Names.named(key))).toInstance(config.getLiveString(key));
      binder.bind(Key.get(INTEGER_VALUE_CLASS, Names.named(key))).toInstance(config.getLiveInt(key));
      binder.bind(Key.get(LONG_VALUE_CLASS, Names.named(key))).toInstance(config.getLiveLong(key));
      binder.bind(Key.get(FLOAT_VALUE_CLASS, Names.named(key))).toInstance(config.getLiveFloat(key));
      binder.bind(Key.get(DOUBLE_VALUE_CLASS, Names.named(key))).toInstance(config.getLiveDouble(key));
      binder.bind(Key.get(BOOLEAN_VALUE_CLASS, Names.named(key))).toInstance(config.getLiveBoolean(key));
    }
  }
}
