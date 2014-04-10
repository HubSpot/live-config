package com.hubspot.liveconfig.value;

import com.google.common.base.Optional;
import com.hubspot.liveconfig.LiveConfig;

import java.util.List;

public class LiveFloat extends LiveValue<Float> {

  public LiveFloat(LiveConfig config, String key) {
    super(config, key, ValueFunctions.toFloat());
  }

  public LiveFloat(LiveConfig config, List<String> keys) {
    super(config, keys, ValueFunctions.toFloat());
  }

  public LiveFloat(LiveConfig config, String key, float fallback) {
    super(config, key, ValueFunctions.toFloat(), fallback);
  }

  public LiveFloat(LiveConfig config, List<String> keys, float fallback) {
    super(config, keys, ValueFunctions.toFloat(), fallback);
  }

  public LiveFloat(LiveConfig config, List<String> keys, Optional<Float> fallback) {
    super(config, keys, ValueFunctions.toFloat(), fallback);
  }

}
