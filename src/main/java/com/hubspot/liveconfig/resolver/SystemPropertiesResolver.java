package com.hubspot.liveconfig.resolver;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class SystemPropertiesResolver extends ForwardingMapResolver {

  private final Map<String, String> properties;

  public SystemPropertiesResolver() {
    this.properties = Maps.fromProperties(System.getProperties());
  }

  @Override
  protected Map<String, String> delegate() {
    return properties;
  }

  @Override
  public Set<String> keySet() {
    return Collections.emptySet();
  }
}
