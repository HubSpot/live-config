package com.hubspot.liveconfig.resolver;

import com.google.common.collect.Maps;

import java.util.Map;

public class SystemPropertiesResolver extends ForwardingMapResolver {

  private final Map<String, String> properties;

  public SystemPropertiesResolver() {
    this.properties = Maps.fromProperties(System.getProperties());
  }

  @Override
  protected Map<String, String> delegate() {
    return properties;
  }
}
