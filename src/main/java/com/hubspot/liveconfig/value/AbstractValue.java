package com.hubspot.liveconfig.value;


import java.util.Optional;

public abstract class AbstractValue<T> extends AbstractOptionalValue<T> {

  /**
   * Override to define getter
   */
  public abstract T get();

  @Override
  public Optional<T> getMaybe() {
    return Optional.ofNullable(get());
  }

}
