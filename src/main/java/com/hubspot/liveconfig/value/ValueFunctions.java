package com.hubspot.liveconfig.value;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ValueFunctions {

  private static final Function<String, Integer> STRING_TO_INTEGER = new Function<String, Integer>() {
    @Override
    public Integer apply(String value) {
      return Integer.parseInt(value);
    }
  };

  public static Function<String, Integer> toInt() {
    return STRING_TO_INTEGER;
  }

  private static final Function<String, Long> STRING_TO_LONG = new Function<String, Long>() {
    @Override
    public Long apply(String value) {
      return Long.parseLong(value);
    }
  };

  public static Function<String, Long> toLong() {
    return STRING_TO_LONG;
  }

  private static final Function<String, Float> STRING_TO_FLOAT = new Function<String, Float>() {
    @Override
    public Float apply(String value) {
      return Float.parseFloat(value);
    }
  };

  public static Function<String, Float> toFloat() {
    return STRING_TO_FLOAT;
  }

  private static final Function<String, Double> STRING_TO_DOUBLE = new Function<String, Double>() {
    @Override
    public Double apply(String value) {
      return Double.parseDouble(value);
    }
  };

  public static Function<String, Double> toDouble() {
    return STRING_TO_DOUBLE;
  }

  private static final Set<String> TRUEISH = ImmutableSet.of("true", "yes", "on", "1");
  private static final Set<String> FALSEISH = ImmutableSet.of("false", "no", "off", "0");

  private static final Function<String, Boolean> STRING_TO_BOOLEAN = new Function<String, Boolean>() {
    @Override
    public Boolean apply(String value) {
      String lower = Strings.nullToEmpty(value).toLowerCase();
      if (TRUEISH.contains(lower)) {
        return true;
      } else if (FALSEISH.contains(lower)) {
        return false;
      }
      throw new IllegalStateException("Unparseable boolean value: " + value);
    }
  };

  public static Function<String, Boolean> toBoolean() {
    return STRING_TO_BOOLEAN;
  }

  private static final Splitter CSV_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

  private static final Function<String, List<String>> STRING_TO_STRING_LIST = new Function<String, List<String>>() {
    @Override
    public List<String> apply(String value) {
      if (Strings.isNullOrEmpty(value)) {
        return Collections.emptyList();
      }
      return Lists.newArrayList(CSV_SPLITTER.split(value));
    }
  };

  public static Function<String, List<String>> toList() {
    return STRING_TO_STRING_LIST;
  }

  private static final Function<String, Map<String, String>> STRING_TO_PROPERTIES = new Function<String, Map<String, String>>() {
    @Override
    public Map<String, String> apply(String value) {
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
    }
  };

  public static Function<String, Map<String, String>> toProperties() {
    return STRING_TO_PROPERTIES;
  }

  public static <V, T> Optional<V> transform(Optional<T> optional, Function<T, V> transform) {
    if (optional.isPresent()) {
      return Optional.of(transform.apply(optional.get()));
    }
    return Optional.absent();
  }

}

