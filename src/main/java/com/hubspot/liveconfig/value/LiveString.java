package com.hubspot.liveconfig.value;

import com.hubspot.liveconfig.LiveConfig;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class LiveString extends LiveValue<String> {

  public LiveString(LiveConfig config, String key) {
    super(config, key, Function.<String>identity());
  }

  public LiveString(LiveConfig config, List<String> keys) {
    super(config, keys, Function.<String>identity());
  }

  public LiveString(LiveConfig config, String key, String fallback) {
    super(config, key, Function.<String>identity(), fallback);
  }

  public LiveString(LiveConfig config, List<String> keys, String fallback) {
    super(config, keys, Function.<String>identity(), fallback);
  }

  public LiveString(LiveConfig config, List<String> keys, Optional<String> fallback) {
    super(config, keys, Function.<String>identity(), fallback);
  }
}
