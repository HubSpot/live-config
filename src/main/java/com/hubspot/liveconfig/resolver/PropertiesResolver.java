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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class PropertiesResolver extends ForwardingMapResolver {
  private static final Logger LOG = Logger.getLogger(PropertiesResolver.class);
  private static final ThreadFactory threadFactory = new ThreadFactory() {
    @Override
    public Thread newThread(Runnable runnable) {
      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
      thread.setDaemon(true);
      return thread;
    }
  };

  private final ScheduledExecutorService executorService;
  private volatile Map<String, String> properties = Maps.newHashMap();

  public PropertiesResolver(final String path) {
    try {
      this.properties = loadProperties(path);
    } catch (IOException e) {
      LOG.warn("Error loading properties from file: " + path);
    }
    this.executorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
    this.executorService.scheduleWithFixedDelay(new Runnable() {
      @Override
      public void run() {
        try {
          properties = loadProperties(path);
        } catch (IOException e) {
          LOG.warn("Error loading properties from file: " + path);
        }
      }
    }, 1, 1, TimeUnit.MINUTES);
  }

  @Override
  protected Map<String, String> delegate() {
    return properties;
  }

  private static Map<String, String> loadProperties(String path) throws IOException {
    File file = new File(path);
    if (file.exists()) {
      Properties properties = new Properties();
      Reader r = new BufferedReader(new FileReader(file));
      properties.load(r);
      r.close();
      return Maps.fromProperties(properties);
    }
    return Collections.emptyMap();
  }
}
