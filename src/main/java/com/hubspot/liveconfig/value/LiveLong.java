package com.hubspot.liveconfig.value;


import com.hubspot.liveconfig.LiveConfig;

import java.util.List;
import java.util.Optional;

public class LiveLong extends LiveValue<Long> {

  public LiveLong(LiveConfig config, String key) {
    super(config, key, Long::parseLong);
  }

  public LiveLong(LiveConfig config, List<String> keys) {
    super(config, keys, Long::parseLong);
  }

  public LiveLong(LiveConfig config, String key, long fallback) {
    super(config, key, Long::parseLong, fallback);
  }

  public LiveLong(LiveConfig config, List<String> keys, long fallback) {
    super(config, keys, Long::parseLong, fallback);
  }

  public LiveLong(LiveConfig config, List<String> keys, Optional<Long> fallback) {
    super(config, keys, Long::parseLong, fallback);
  }
}
