package com.hubspot.liveconfig.value;

import com.google.common.base.Optional;
import com.hubspot.liveconfig.LiveConfig;

import java.util.List;

public class LiveDouble extends LiveValue<Double> {

  public LiveDouble(LiveConfig config, String key) {
    super(config, key, ValueFunctions.toDouble());
  }

  public LiveDouble(LiveConfig config, List<String> keys) {
    super(config, keys, ValueFunctions.toDouble());
  }

  public LiveDouble(LiveConfig config, String key, double fallback) {
    super(config, key, ValueFunctions.toDouble(), fallback);
  }

  public LiveDouble(LiveConfig config, List<String> keys, double fallback) {
    super(config, keys, ValueFunctions.toDouble(), fallback);
  }

  public LiveDouble(LiveConfig config, List<String> keys, Optional<Double> fallback) {
    super(config, keys, ValueFunctions.toDouble(), fallback);
  }

}
