package com.hubspot.liveconfig.resolver;

import java.util.Optional;
import java.util.Set;

public interface Resolver {
  Optional<String> get(String key);
  Set<String> keySet();
}
