package com.hubspot.liveconfig.value;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public interface Value<T> {
  T get();
  Optional<T> getMaybe();
  T or(T defaultValue);
  Optional<T> or(Optional<T> secondChoice);
  T orNull();
  <V> Value<V> transform(Function<T, V> transform);
}
