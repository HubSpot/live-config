package com.hubspot.liveconfig.value;

import com.google.common.base.Optional;

public abstract class AbstractValue<T> extends AbstractOptionalValue<T> {

  /**
   * Override to define getter
   */
  public abstract T get();

  @Override
  public Optional<T> getMaybe() {
    return Optional.fromNullable(get());
  }

}
