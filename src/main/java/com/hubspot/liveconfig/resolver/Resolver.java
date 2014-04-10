package com.hubspot.liveconfig.resolver;

import com.google.common.base.Optional;

import java.util.Set;

public interface Resolver {
  Optional<String> get(String key);
  Set<String> keySet();
}
