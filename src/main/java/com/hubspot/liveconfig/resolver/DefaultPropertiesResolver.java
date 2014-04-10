package com.hubspot.liveconfig.resolver;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class DefaultPropertiesResolver extends MapResolver {

  private static final Logger LOG = Logger.getLogger(DefaultPropertiesResolver.class);
  private static final String DUPLICATE_MESSAGE = "There is a duplicate property for %s. It was first set by %s project, but then set again by %s project";

  public DefaultPropertiesResolver(String... packages) {
    super(new DefaultPropertiesScanner(packages).asMap());
  }

  private static class DefaultPropertiesScanner {
    private static final FilterBuilder FILTER = new FilterBuilder().include("\\w+\\.default\\.properties");

    private final Reflections reflections;

    public DefaultPropertiesScanner(String... packages) {
      ConfigurationBuilder cfg = new ConfigurationBuilder().addUrls(ClasspathHelper.forJavaClassPath())
          .addUrls(ClasspathHelper.forPackage(""))
          .filterInputsBy(FILTER)
          .setScanners(new ResourcesScanner());

      for (String name : packages) {
        cfg.addUrls(ClasspathHelper.forPackage(name));
      }

      reflections = new Reflections(cfg);
    }

    public Map<String, String> asMap() {
      final Properties properties = new Properties();
      final Map<Object, String> keysProjects = Maps.newHashMap();

      for (String resourceName : reflections.getResources(FILTER)) {
        final InputStream projectInputStream = getClass().getResourceAsStream('/' + resourceName);

        if (projectInputStream == null) {
          LOG.debug(String.format("No default properties file found for name: " + resourceName));
          continue;
        }

        final Properties projectProperties = new Properties();
        try {
          projectProperties.load(projectInputStream);
          projectInputStream.close();
        } catch (IOException e) {
          throw Throwables.propagate(e);
        }

        LOG.debug(String.format("Read %d properties from default properties file: %s", projectProperties.size(), resourceName));
        String project = resourceName.split("\\.")[0];
        for (Map.Entry<Object, Object> entry : projectProperties.entrySet()) {
          if (properties.containsKey(entry.getKey())) {
            throw new IllegalStateException(String.format(DUPLICATE_MESSAGE, entry.getKey(), keysProjects.get(entry.getKey()), project));
          }
          keysProjects.put(entry.getKey(), project);
          properties.put(entry.getKey(), entry.getValue());
        }
      }

      return Maps.fromProperties(properties);
    }
  }

}
