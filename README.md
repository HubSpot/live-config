# LiveConfig

Live configuration for Java projects.

## Basic Usage

`LiveConfig` gives you access to configuration parameters that can be loaded from a variety of sources. You create a `LiveConfig` like so...

```java
// a simple config, backed by a map
Map<String, String> propertyMap = Maps.newHashMap();
LiveConfig config = LiveConfig.fromMap(propertyMap);
```

You can create more useful configurations by adding more sources. This example will first look for configuration parameters in env vars, falling back to system properties and finally to default properties that are included on your classpath.

```java
LiveConfig config = LiveConfig.builder()
    .usingEnvironmentVariables()
    .usingSystemProperties()
    .usingPropertiesFile("/etc/hubspot.properties")
    .usingDefaultProperties("com.hubspot")
    .build();
```

The `LiveConfig` class exposes simple getters such as `getInt(String key)` which can be used to access properties from your backing stores. There are getters for most primitive types, as well as getters that expose `Optional` values so you can gracefully handle missing values in your code. Here are some examples...

```java
// basic getters
String name = config.getString("first.name");
int count = config.getInt("widget.count");
boolean on = config.getBoolean("lights.on");
long expiration = config.getLong("expiration.time");
List<String> users = config.getList("user.names");

// optionals
String name = config.getStringMaybe("first.name").orNull();
int count = config.getIntMaybe("widget.count").or(0);

// getters with fallbacks
String name = config.getString("full.name", "first.name");
```

## Value and Live Values

One of the coolest features of LiveConfig is LiveValues. These make it very easy to use configuration parameters to your application that get updated in realtime. Like the other `LiveConfig` getters, these also come in flavors for most primitive types. Here are some examples...

```java
Value<String> name = config.getLiveString("first.name");
name.get(); // Trevor
... // change 'first.name' in your backing store
name.get(); // James

// you can use these like Optionals too
Value<Boolean> on = config.getLiveBoolean("lights.on");
lights.enable(on.or(false));
```

In general it's better to use `Value<T>` in your method signatures rather than the concrete `LiveXXX` classes, so we made some shortcuts to facilitate testing in your apps. You can use `FixedValue` to quicky wrap constants without having to deal with LiveValues or LiveConfig.

```java
Value<Boolean> on = FixedValue.of(true);
on.get(); // true

Value<Boolean> on = FixedValue.absent();
on.get(); // throws IllegalStateException
on.or(false); // false;
```

## Guice

Using Guice with LiveConfig is very easy. Simply use the provided `LiveConfigModule` and bindings will be created for all properties enumerated by the specified `LiveConfig` instance.

```java
Map<String, String> properties = Maps.newHashMap();
properties.put("first.name", "Trevor");
properties.put("widget.count", "8");
properties.put("lights.on", "true");

LiveConfig config = LiveConfig.fromMap(properties);
Injector injector = Guice.createInjector(new LiveConfigModule(config));
Widgets widgets = injector.getInstance(Widgets.class);
widgets.countWidgets(); // 8

// ... elsewhere
class Widgets {
    private Value<Integer> widgetCount;

    @Inject
    Widgets(@Named("widget.count") Value<Integer> widgetCount) {
      this.widgetCount = widgetCount;
    }

    int countWidgets() {
      return widgetCount.get();
    }
}
```

## Advanced Usage

Obviously static maps by themselves are not very useful. Additionally LiveConfig has out of the box support for environment variables, system properties and default properties files bundled with your jars. The real fun begins when you hook LiveConfig up to custom resolver that can fetch configuration from an external service, like a database or an API. Here's a simple example...


```java
class ConfigApiResolver implements Resolver {
    private final AtomicReference mapReference;
    private ScheduledExecutorService executor;

    public ConfigApiResolver() {
        mapReference = new AtomicReference(fetchMap());

        // update config every minute
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                mapReference.set(fetchMap());
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public Optional<String> get(String key) {
        return Optional.fromNullable(mapReference.get().get(key));
    }

    @Override
    public Set<String> keySet() {
        return mapReference.get().keySet();
    }

    private Map<String, String> fetchMap() {
      // ... hit your api here
    }
}
```

You can add your custom resolver to the chain like so...

```java
LiveConfig config = LiveConfig.builder()
    .usingEnvironmentVariables()
    .usingSystemProperties()
    .usingResolver(new ConfigApiResolver()) // <-- your new resolver
    .usingDefaultProperties("com.hubspot")
    .build();
```
