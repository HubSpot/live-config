package com.hubspot.liveconfig.resolver;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

import java.util.Map;

public class EnvironmentResolver extends ForwardingMapResolver {

  private final Map<String, String> envMap;

  public EnvironmentResolver() {
    this.envMap = transformEnvMap(System.getenv());
  }

  @VisibleForTesting
  EnvironmentResolver(Map<String, String> map) {
    this.envMap = transformEnvMap(map);
  }

  @Override
  protected Map<String, String> delegate() {
    return envMap;
  }

  private static Map<String, String> transformEnvMap(Map<String, String> envMap) {
    Map<String, String> map = Maps.newHashMapWithExpectedSize(envMap.size());
    for (Map.Entry<String, String> entry : envMap.entrySet()) {
      map.put(envToKey(entry.getKey()), entry.getValue());
    }
    return map;
  }

  private static String envToKey(String env) {
    return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, env).replace('-', '.');
  }
}
