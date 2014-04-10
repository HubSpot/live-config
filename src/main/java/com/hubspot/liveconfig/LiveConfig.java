package com.hubspot.liveconfig;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hubspot.liveconfig.resolver.*;
import com.hubspot.liveconfig.value.LiveBoolean;
import com.hubspot.liveconfig.value.LiveDouble;
import com.hubspot.liveconfig.value.LiveFloat;
import com.hubspot.liveconfig.value.LiveInt;
import com.hubspot.liveconfig.value.LiveLong;
import com.hubspot.liveconfig.value.LiveString;
import com.hubspot.liveconfig.value.LiveValue;
import com.hubspot.liveconfig.value.ValueFunctions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class LiveConfig {

  public static class Builder {

    private final List<Resolver> resolvers = Lists.newArrayList();

    private Builder() {
    }

    public Builder usingEnvironmentVariables() {
      resolvers.add(new EnvironmentResolver());
      return this;
    }

    public Builder usingSystemProperties() {
      resolvers.add(new SystemPropertiesResolver());
      return this;
    }

    public Builder usingDefaultProperties(String... packages) {
      resolvers.add(new DefaultPropertiesResolver(packages));
      return this;
    }

    public Builder usingMap(Map<String, String> map) {
      resolvers.add(new MapResolver(map));
      return this;
    }

    public Builder usingProperties(Properties properties) {
      return usingMap(Maps.fromProperties(properties));
    }

    public Builder usingPropertiesFile(String path) {
      resolvers.add(new PropertiesResolver(path));
      return this;
    }

    public Builder usingResolver(Resolver resolver) {
      resolvers.add(resolver);
      return this;
    }

    public LiveConfig build() {
      return new LiveConfig(new ChainedResolver(resolvers));
    }

  }

  public static Builder builder() {
    return new Builder();
  }

  public static LiveConfig fromMap(Map<String, String> map) {
    return new LiveConfig(new MapResolver(map));
  }

  private final Resolver resolver;

  private LiveConfig(Resolver resolver) {
    this.resolver = resolver;
  }

  //
  // Static Value Getters
  //

  public Optional<String> getStringMaybe(String key) {
    return resolver.get(key);
  }

  public Optional<String> getStringMaybe(String... keys) {
    return getStringMaybe(Arrays.asList(keys));
  }

  public Optional<String> getStringMaybe(List<String> keys) {
    for (String key : keys) {
      Optional<String> valueMaybe = getStringMaybe(key);
      if (valueMaybe.isPresent()) {
        return valueMaybe;
      }
    }
    return Optional.absent();
  }

  public String getString(String... keys) {
    return getStringMaybe(keys).orNull();
  }

  public Optional<Integer> getIntMaybe(String... keys) {
    return getValueMaybe(Arrays.asList(keys), ValueFunctions.toInt());
  }

  public int getInt(String... keys) {
    return getIntMaybe(keys).get();
  }

  public Optional<Long> getLongMaybe(String... keys) {
    return getValueMaybe(Arrays.asList(keys), ValueFunctions.toLong());
  }

  public long getLong(String... keys) {
    return getLongMaybe(keys).get();
  }
  
  public Optional<Float> getFloatMaybe(String... keys) {
    return getValueMaybe(Arrays.asList(keys), ValueFunctions.toFloat());
  }
  
  public float getFloat(String... keys) {
    return getFloatMaybe(keys).get();
  }

  public Optional<Boolean> getBooleanMaybe(String... keys) {
    return getValueMaybe(Arrays.asList(keys), ValueFunctions.toBoolean());
  }

  public boolean getBoolean(String... keys) {
    return getBooleanMaybe(keys).get();
  }

  public Optional<Double> getDoubleMaybe(String... keys) {
    return getValueMaybe(Arrays.asList(keys), ValueFunctions.toDouble());
  }

  public double getDouble(String... keys) {
    return getDoubleMaybe(keys).get();
  }

  public <T> Optional<T> getValueMaybe(List<String> keys, Function<String, T> func) {
    return ValueFunctions.transform(getStringMaybe(keys), func);
  }

  public Map<String, String> getProperties(String... keys) {
    return getProperties(Arrays.asList(keys));
  }

  public Map<String, String> getProperties(List<String> keys) {
    return getValueMaybe(keys, ValueFunctions.toProperties()).or(Collections.<String, String>emptyMap());
  }

  public List<String> getList(String... keys) {
    return getList(Arrays.asList(keys));
  }

  public List<String> getList(List<String> keys) {
    return getValueMaybe(keys, ValueFunctions.toList()).or(Collections.<String>emptyList());
  }

  public <T> List<T> getList(String key, Function<String, T> func) {
    return getList(Arrays.asList(key), func);
  }

  public <T> List<T> getList(List<String> keys, Function<String, T> func) {
    return Lists.newArrayList(Lists.transform(getList(keys), func));
  }

  //
  // Live Value Getters
  //

  public LiveString getLiveString(String... keys) {
    return new LiveString(this, Arrays.asList(keys));
  }

  public LiveString getLiveString(List<String> keys) {
    return new LiveString(this, keys);
  }

  public LiveString getLiveString(String key, String fallback) {
    return new LiveString(this, key, fallback);
  }

  public LiveString getLiveString(List<String> keys, String fallback) {
    return new LiveString(this, keys, fallback);
  }

  public LiveInt getLiveInt(String... keys) {
    return new LiveInt(this, Arrays.asList(keys));
  }

  public LiveInt getLiveInt(String key, int fallback) {
    return new LiveInt(this, key, fallback);
  }

  public LiveInt getLiveInt(List<String> keys) {
    return new LiveInt(this, keys);
  }

  public LiveInt getLiveInt(List<String> keys, int fallback) {
    return new LiveInt(this, keys, fallback);
  }

  public LiveLong getLiveLong(String... keys) {
    return new LiveLong(this, Arrays.asList(keys));
  }

  public LiveLong getLiveLong(String key, long fallback) {
    return new LiveLong(this, key, fallback);
  }

  public LiveLong getLiveLong(List<String> keys) {
    return new LiveLong(this, keys);
  }

  public LiveLong getLiveLong(List<String> keys, long fallback) {
    return new LiveLong(this, keys, fallback);
  }

  public LiveBoolean getLiveBoolean(String... keys) {
    return new LiveBoolean(this, Arrays.asList(keys));
  }

  public LiveBoolean getLiveBoolean(String key, boolean fallback) {
    return new LiveBoolean(this, key, fallback);
  }

  public LiveBoolean getLiveBoolean(List<String> keys) {
    return new LiveBoolean(this, keys);
  }

  public LiveBoolean getLiveBoolean(List<String> keys, boolean fallback) {
    return new LiveBoolean(this, keys, fallback);
  }

  public LiveFloat getLiveFloat(String... keys) {
    return new LiveFloat(this, Arrays.asList(keys));
  }

  public LiveFloat getLiveFloat(String key, float fallback) {
    return new LiveFloat(this, key, fallback);
  }

  public LiveFloat getLiveFloat(List<String> keys) {
    return new LiveFloat(this, keys);
  }

  public LiveFloat getLiveFloat(List<String> keys, float fallback) {
    return new LiveFloat(this, keys, fallback);
  }

  public LiveDouble getLiveDouble(String... keys) {
    return new LiveDouble(this, Arrays.asList(keys));
  }

  public LiveDouble getLiveDouble(String key, double fallback) {
    return new LiveDouble(this, key, fallback);
  }

  public LiveDouble getLiveDouble(List<String> keys) {
    return new LiveDouble(this, keys);
  }

  public LiveDouble getLiveDouble(List<String> keys, double fallback) {
    return new LiveDouble(this, keys, fallback);
  }

  public <T> LiveValue<T> getLiveValue(String key, Function<String, T> func) {
    return new LiveValue<T>(this, key, func);
  }

  public <T> LiveValue<T> getLiveValue(String key, Function<String, T> func, T fallback) {
    return new LiveValue<T>(this, key, func, fallback);
  }

  public <T> LiveValue<T> getLiveValue(List<String> keys, Function<String, T> func) {
    return new LiveValue<T>(this, keys, func);
  }

  public <T> LiveValue<T> getLiveValue(List<String> keys, Function<String, T> func, T fallback) {
    return new LiveValue<T>(this, keys, func, fallback);
  }

  //
  // Map Delegates
  //

  public Map<String, String> asMap() {
    Map<String, String> map = Maps.newTreeMap();
    for (String key : resolver.keySet()) {
      map.put(key, resolver.get(key).orNull());
    }
    return Collections.unmodifiableMap(map);
  }

}
