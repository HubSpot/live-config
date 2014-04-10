package com.hubspot.liveconfig.value;

import com.google.common.base.Optional;
import com.hubspot.liveconfig.LiveConfig;

import java.util.List;

public class LiveLong extends LiveValue<Long> {

  public LiveLong(LiveConfig config, String key) {
    super(config, key, ValueFunctions.toLong());
  }

  public LiveLong(LiveConfig config, List<String> keys) {
    super(config, keys, ValueFunctions.toLong());
  }

  public LiveLong(LiveConfig config, String key, long fallback) {
    super(config, key, ValueFunctions.toLong(), fallback);
  }

  public LiveLong(LiveConfig config, List<String> keys, long fallback) {
    super(config, keys, ValueFunctions.toLong(), fallback);
  }

  public LiveLong(LiveConfig config, List<String> keys, Optional<Long> fallback) {
    super(config, keys, ValueFunctions.toLong(), fallback);
  }
  
}
