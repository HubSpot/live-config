package com.hubspot.liveconfig.value;


import com.hubspot.liveconfig.LiveConfig;

import java.util.List;
import java.util.Optional;

public class LiveDouble extends LiveValue<Double> {

  public LiveDouble(LiveConfig config, String key) {
    super(config, key, Double::parseDouble);
  }

  public LiveDouble(LiveConfig config, List<String> keys) {
    super(config, keys, Double::parseDouble);
  }

  public LiveDouble(LiveConfig config, String key, double fallback) {
    super(config, key, Double::parseDouble, fallback);
  }

  public LiveDouble(LiveConfig config, List<String> keys, double fallback) {
    super(config, keys, Double::parseDouble, fallback);
  }

  public LiveDouble(LiveConfig config, List<String> keys, Optional<Double> fallback) {
    super(config, keys, Double::parseDouble, fallback);
  }

}
