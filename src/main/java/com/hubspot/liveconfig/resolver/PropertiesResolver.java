package com.hubspot.liveconfig.resolver;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class PropertiesResolver extends MapResolver {

  private static final Logger LOG = Logger.getLogger(PropertiesResolver.class);

  public PropertiesResolver(String path) {
    super(loadProperties(path));
  }

  private static Map<String, String> loadProperties(String path) {
    File file = new File(path);
    if (file.exists()) {
      Properties properties = new Properties();
      try {
        Reader r = new BufferedReader(new FileReader(file));
        properties.load(r);
        r.close();
      } catch (IOException e) {
        LOG.warn(String.format("Error loading properties from file: %s", file.getPath()), e);
      }
      return Maps.fromProperties(properties);
    }
    return Collections.emptyMap();
  }

}
