package com.hubspot.liveconfig.resolver;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ChainedResolver implements Resolver {

  private final List<Resolver> resolvers = Collections.synchronizedList(Lists.<Resolver>newArrayList());

  public ChainedResolver(List<Resolver> resolvers) {
    this.resolvers.addAll(resolvers);
  }

  @Override
  public Optional<String> get(String key) {
    for (Resolver resolver : resolvers) {
      Optional<String> value = resolver.get(key);
      if (value.isPresent()) {
        return value;
      }
    }
    return Optional.absent();
  }

  @Override
  public Set<String> keySet() {
    Set<String> keySet = Sets.newHashSet();
    for (Resolver resolver : resolvers) {
      keySet.addAll(resolver.keySet());
    }
    return keySet;
  }

}
