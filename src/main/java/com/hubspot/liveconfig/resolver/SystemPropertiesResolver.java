package com.hubspot.liveconfig.resolver;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Set;

public class SystemPropertiesResolver extends MapResolver {

  public SystemPropertiesResolver() {
    super(Maps.fromProperties(System.getProperties()));
  }

  @Override
  public Set<String> keySet() {
    return Collections.emptySet();
  }
}
