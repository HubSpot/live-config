package com.hubspot.liveconfig.resolver;

import com.google.common.base.Optional;

import java.util.Map;
import java.util.Set;

public class MapResolver implements Resolver {

  private final Map<String, String> map;

  public MapResolver(Map<String, String> map) {
    this.map = map;
  }

  @Override
  public Optional<String> get(String key) {
    return Optional.fromNullable(map.get(key));
  }

  @Override
  public Set<String> keySet() {
    return map.keySet();
  }

}
