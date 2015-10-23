package com.hubspot.liveconfig.value;

import com.google.common.base.Functions;
import com.google.common.base.Optional;
import com.hubspot.liveconfig.LiveConfig;

import java.util.List;

public class LiveString extends LiveValue<String> {

  public LiveString(LiveConfig config, String key) {
    super(config, key, Functions.<String>identity());
  }

  public LiveString(LiveConfig config, List<String> keys) {
    super(config, keys, Functions.<String>identity());
  }

  public LiveString(LiveConfig config, String key, String fallback) {
    super(config, key, Functions.<String>identity(), fallback);
  }

  public LiveString(LiveConfig config, List<String> keys, String fallback) {
    super(config, keys, Functions.<String>identity(), fallback);
  }

  public LiveString(LiveConfig config, List<String> keys, Optional<String> fallback) {
    super(config, keys, Functions.<String>identity(), fallback);
  }
}
