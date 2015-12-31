package com.hubspot.liveconfig.value;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;

public class ValueFunctions {
  private static final Set<String> TRUEISH = ImmutableSet.of("true", "yes", "on", "1");
  private static final Set<String> FALSEISH = ImmutableSet.of("false", "no", "off", "0");

  private static final Function<String, Boolean> STRING_TO_BOOLEAN = value -> {
    String lower = Strings.nullToEmpty(value).toLowerCase();
    if (TRUEISH.contains(lower)) {
      return true;
    } else if (FALSEISH.contains(lower)) {
      return false;
    }
    throw new IllegalStateException("Unparseable boolean value: " + value);
  };

  public static Function<String, Boolean> toBoolean() {
    return STRING_TO_BOOLEAN;
  }

  private static final Splitter CSV_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

  private static final Function<String, List<String>> STRING_TO_STRING_LIST = value -> {
    if (Strings.isNullOrEmpty(value)) {
      return Collections.emptyList();
    }
    return Lists.newArrayList(CSV_SPLITTER.split(value));
  };

  public static Function<String, List<String>> toList() {
    return STRING_TO_STRING_LIST;
  }

  private static final Function<String, Map<String, String>> STRING_TO_PROPERTIES = value -> {
    if (Strings.isNullOrEmpty(value)) {
      return Collections.emptyMap();
    }
    Properties props = new Properties();
    try {
      props.load(new StringReader(value));
    } catch (IOException e) {
      throw new IllegalStateException("Unparseable properties value: " + value, e);
    }
    return Maps.fromProperties(props);
  };

  public static Function<String, Map<String, String>> toProperties() {
    return STRING_TO_PROPERTIES;
  }

  public static <V, T> Optional<V> transform(Optional<T> optional, final Function<T, V> transform) {
    return optional.map(value -> transform.apply(value));
  }
}

