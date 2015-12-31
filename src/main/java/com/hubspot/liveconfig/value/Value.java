package com.hubspot.liveconfig.value;


import java.util.Optional;
import java.util.function.Function;

public interface Value<T> {
  T get();
  Optional<T> getMaybe();
  T or(T defaultValue);
  Optional<T> or(Optional<T> secondChoice);
  T orNull();
  <V> Value<V> transform(Function<T, V> transform);
}
