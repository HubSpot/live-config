package com.hubspot.liveconfig.value;

import com.google.common.base.Optional;
import com.hubspot.liveconfig.LiveConfig;

import java.util.List;

public class LiveInt extends LiveValue<Integer> {

  public LiveInt(LiveConfig config, String key) {
    super(config, key, ValueFunctions.toInt());
  }

  public LiveInt(LiveConfig config, List<String> keys) {
    super(config, keys, ValueFunctions.toInt());
  }

  public LiveInt(LiveConfig config, String key, int fallback) {
    super(config, key, ValueFunctions.toInt(), fallback);
  }

  public LiveInt(LiveConfig config, List<String> keys, int fallback) {
    super(config, keys, ValueFunctions.toInt(), fallback);
  }

  public LiveInt(LiveConfig config, List<String> keys, Optional<Integer> fallback) {
    super(config, keys, ValueFunctions.toInt(), fallback);
  }
}
