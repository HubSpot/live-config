package com.hubspot.liveconfig.value;


import com.hubspot.liveconfig.LiveConfig;

import java.util.List;
import java.util.Optional;

public class LiveBoolean extends LiveValue<Boolean> {

  public LiveBoolean(LiveConfig config, String key) {
    super(config, key, ValueFunctions.toBoolean());
  }

  public LiveBoolean(LiveConfig config, String key, boolean fallback) {
    super(config, key, ValueFunctions.toBoolean(), fallback);
  }

  public LiveBoolean(LiveConfig config, List<String> keys) {
    super(config, keys, ValueFunctions.toBoolean());
  }

  public LiveBoolean(LiveConfig config, List<String> keys, boolean fallback) {
    super(config, keys, ValueFunctions.toBoolean(), fallback);
  }

  public LiveBoolean(LiveConfig config, List<String> keys, Optional<Boolean> fallback) {
    super(config, keys, ValueFunctions.toBoolean(), fallback);
  }

}
