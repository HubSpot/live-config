package com.hubspot.liveconfig.value;

import com.google.common.base.Optional;

public class FixedValue<T> extends AbstractOptionalValue<T> {
  private final Optional<T> value;

  public FixedValue() {
    this.value = Optional.absent();
  }

  public FixedValue(T value) {
    this.value = Optional.of(value);
  }

  @Override
  public Optional<T> getMaybe() {
    return value;
  }

  public static <T> FixedValue<T> of(T value) {
    return new FixedValue<T>(value);
  }

  public static <T> FixedValue<T> absent() {
    return new FixedValue<T>();
  }

  @Override
  public String toString() {
    return String.format("FixedValue{}=%s", value.orNull());
  }
}
