package com.hubspot.liveconfig.resolver;

import java.util.Map;

public class MapResolver extends ForwardingMapResolver {
  private final Map<String, String> properties;

  public MapResolver(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  protected Map<String, String> delegate() {
    return properties;
  }
}
