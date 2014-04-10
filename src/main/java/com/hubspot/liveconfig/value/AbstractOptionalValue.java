package com.hubspot.liveconfig.value;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public abstract class AbstractOptionalValue<T> implements Value<T> {

  /**
   * Override to define optional getter
   */
  public abstract Optional<T> getMaybe();

  @Override
  public T get() {
    return getMaybe().get();
  }

  @Override
  public T or(T defaultValue) {
    return getMaybe().or(defaultValue);
  }

  @Override
  public T orNull() {
    return getMaybe().orNull();
  }

  @Override
  public Optional<T> or(Optional<T> secondChoice) {
    return getMaybe().or(secondChoice);
  }

  @Override
  public <V> Value<V> transform(final Function<T, V> func) {
    final Value<T> parent = this;
    return new AbstractOptionalValue<V>() {
      @Override
      public Optional<V> getMaybe() {
        return ValueFunctions.transform(parent.getMaybe(), func);
      }
    };
  }

}
