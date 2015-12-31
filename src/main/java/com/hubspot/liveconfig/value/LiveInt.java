package com.hubspot.liveconfig.value;

import com.hubspot.liveconfig.LiveConfig;

import java.util.List;
import java.util.Optional;

public class LiveInt extends LiveValue<Integer> {

  public LiveInt(LiveConfig config, String key) {
    super(config, key, Integer::parseInt);
  }

  public LiveInt(LiveConfig config, List<String> keys) {
    super(config, keys, Integer::parseInt);
  }

  public LiveInt(LiveConfig config, String key, int fallback) {
    super(config, key, Integer::parseInt, fallback);
  }

  public LiveInt(LiveConfig config, List<String> keys, int fallback) {
    super(config, keys, Integer::parseInt, fallback);
  }

  public LiveInt(LiveConfig config, List<String> keys, Optional<Integer> fallback) {
    super(config, keys, Integer::parseInt, fallback);
  }
}
