package com.hubspot.liveconfig.value;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.hubspot.liveconfig.LiveConfig;

import java.util.List;

public class LiveValue<T> extends AbstractOptionalValue<T> {

  private final LiveConfig config;
  private final List<String> keys;
  private final Function<String, T> transform;
  private final Optional<T> fallback;

  public LiveValue(LiveConfig config, String key, Function<String, T> transform) {
    this(config, ImmutableList.of(key), transform);
  }

  public LiveValue(LiveConfig config, String key, Function<String, T> transform, T fallback) {
    this(config, ImmutableList.of(key), transform, fallback);
  }

  public LiveValue(LiveConfig config, List<String> keys, Function<String, T> transform) {
    this(config, keys, transform, Optional.<T>absent());
  }

  public LiveValue(LiveConfig config, List<String> keys, Function<String, T> transform, T fallback) {
    this(config, keys, transform, Optional.of(fallback));
  }

  public LiveValue(LiveConfig config, List<String> keys, Function<String, T> transform, Optional<T> fallback) {
    this.config = config;
    this.keys = keys;
    this.transform = transform;
    this.fallback = fallback;
  }

  @Override
  public Optional<T> getMaybe() {
    return config.getValueMaybe(keys, transform).or(fallback);
  }

  @Override
  public String toString() {
    return String.format("LiveValue{%s}=%s", Joiner.on(",").join(keys), orNull());
  }
}
