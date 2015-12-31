package com.hubspot.liveconfig.resolver;


import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class ForwardingMapResolver implements Resolver {

  protected abstract Map<String, String> delegate();

  @Override
  public Optional<String> get(String key) {
    return Optional.ofNullable(delegate().get(key));
  }

  @Override
  public Set<String> keySet() {
    return delegate().keySet();
  }

}
