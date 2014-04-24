package com.hubspot.liveconfig.resolver;

import com.google.common.base.Optional;

import java.util.Map;
import java.util.Set;

public abstract class ForwardingMapResolver implements Resolver {

  protected abstract Map<String, String> delegate();

  @Override
  public Optional<String> get(String key) {
    return Optional.fromNullable(delegate().get(key));
  }

  @Override
  public Set<String> keySet() {
    return delegate().keySet();
  }

}
