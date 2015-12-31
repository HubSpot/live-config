package com.hubspot.liveconfig.value;


import java.util.Optional;
import java.util.function.Function;

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
    return getMaybe().orElse(defaultValue);
  }

  @Override
  public T orNull() {
    return getMaybe().orElse(null);
  }

  @Override
  public Optional<T> or(Optional<T> secondChoice) {
    final Optional<T> maybe = getMaybe();
    if (!maybe.isPresent()) {
      return secondChoice;
    }
    return maybe;
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
