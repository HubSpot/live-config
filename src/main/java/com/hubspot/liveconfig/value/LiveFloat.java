package com.hubspot.liveconfig.value;


import com.hubspot.liveconfig.LiveConfig;

import java.util.List;
import java.util.Optional;

public class LiveFloat extends LiveValue<Float> {

  public LiveFloat(LiveConfig config, String key) {
    super(config, key, Float::parseFloat);
  }

  public LiveFloat(LiveConfig config, List<String> keys) {
    super(config, keys, Float::parseFloat);
  }

  public LiveFloat(LiveConfig config, String key, float fallback) {
    super(config, key, Float::parseFloat, fallback);
  }

  public LiveFloat(LiveConfig config, List<String> keys, float fallback) {
    super(config, keys, Float::parseFloat, fallback);
  }

  public LiveFloat(LiveConfig config, List<String> keys, Optional<Float> fallback) {
    super(config, keys, Float::parseFloat, fallback);
  }

}
