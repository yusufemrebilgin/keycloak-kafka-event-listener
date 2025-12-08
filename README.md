# Keycloak Kafka Event Listener SPI

A Keycloak Service Provider Interface (SPI) extension publishes Keycloak events as JSON messages to Kafka topics, 
enabling integration with event-driven architectures and stream processing systems.

It supports user events and admin operations, allows configurable event filtering, reads configuration 
from environment variables (supporting all official Kafka producer properties), and ensures graceful shutdown 
with producer flushing. It is disabled by default and falls back safely to a no-op listener if Kafka is unavailable 
or initialization fails.

## Enabling the SPI

To enable the Kafka event listener set the environment variable:

```
SPI_KAFKA_ENABLED=true
```

If the variable is missing or set to `false` (case-insensitive), the SPI will remain disabled and Keycloak will
use a no-op provider.

## Configuration

All configuration entries supported by the official Kafka producer can be passed as environment variables. 
The environment variable names follow the pattern:

```
SPI_KAFKA_<PRODUCER_CONFIG_PROPERTY_ENUM_NAME>
```

where `<PRODUCER_CONFIG_PROPERTY_ENUM_NAME>` corresponds to the enum constant names defined in the SPI
([ProducerConfigProperties.java](https://github.com/yusufemrebilgin/keycloak-kafka-event-listener/blob/main/src/main/java/com/github/yusufemrebilgin/keycloak/kafka/ProducerConfigProperties.java)).

Examples:

- `SPI_KAFKA_BOOTSTRAP_SERVERS` (default: `localhost:9092`)
- `SPI_KAFKA_CLIENT_ID` (default: `keycloak-event-listener`)
- `SPI_KAFKA_KEY_SERIALIZER` (default: `org.apache.kafka.common.serialization.StringSerializer`)
- `SPI_KAFKA_VALUE_SERIALIZER` (default: `org.apache.kafka.common.serialization.StringSerializer`)
- `SPI_KAFKA_TOPIC_PREFIX` (custom property used by the SPI; default: `keycloak.events`)

<br>

> **Implementation note:** The SPI iterates over `ProducerConfigProperties.values()` and reads
> `System.getenv("SPI_KAFKA_" + property.name())` to set each supported Kafka producer property.
> Use the corresponding enum constant name as the suffix.

### Event Filtering

You can restrict which events are published by providing comma-separated lists of user events or admin operations:

- `SPI_KAFKA_INCLUDED_EVENT_TYPES`:
  Comma-separated [EventType](https://www.keycloak.org/docs-api/latest/javadocs/org/keycloak/events/EventType.html)
  names. If not set or empty, all user event types are included.
- `SPI_KAFKA_INCLUDED_OPERATION_TYPES`:
  Comma-separated [OperationType](https://www.keycloak.org/docs-api/latest/javadocs/org/keycloak/events/admin/OperationType.html)
  names. If not set or empty, all admin operations types are included.

Values are case-insensitive (they are normalized to uppercase internally). Invalid names will cause initialization to
fail with a clear error listing valid enum names.

## Running the Kafka Event Listener SPI

There are three main ways to run the SPI:

### Option 1: Using the provided script

Run the automated script to build the SPI JAR and start Keycloak with Kafka:

```bash
scripts/build-and-run.sh
```

This handles both building the JAR and running Docker Compose

### Option 2: Using Docker Compose only

If the SPI JAR is already built, you can start the environment directly:

```bash
docker compose up -d
```

### Option 3: Manual build and run

Build the SPI JAR with Maven wrapper and then start Docker Compose:

```bash
./mvnw clean package
docker compose up --build -d
```

When everything runs correctly, you should see logs similar to:

```terminaloutput
2025-12-02 11:24:10,789 INFO  [com.github.yusufemrebilgin.keycloak.kafka.KafkaEventListenerProviderFactory] (main) Initializing [KafkaEventListenerProvider]
2025-12-02 11:24:10,789 INFO  [com.github.yusufemrebilgin.keycloak.kafka.KafkaConfigurationProperties] (main) Loading Kafka configuration properties from environment variables
2025-12-02 11:24:10,791 INFO  [com.github.yusufemrebilgin.keycloak.kafka.KafkaConfigurationProperties] (main) Kafka configuration loaded successfully (Bootstrap Servers: kafka:9092)
2025-12-02 11:24:11,028 INFO  [com.github.yusufemrebilgin.keycloak.kafka.KafkaEventListenerProviderFactory] (main) Kafka producer initialized successfully
2025-12-02 11:24:11,028 INFO  [com.github.yusufemrebilgin.keycloak.kafka.KafkaEventListenerProviderFactory] (main) Event filtering initialized - [User Events: 2, Admin Operations: 2]
```

### Activating the Kafka Event Listener in Keycloak

Once Keycloak is running, access the [Admin Console](http://localhost:8080) using the default credentials and activate
the SPI:

1. Click **Realm Settings** in the left-hand menu.
2. Navigate to the **Events** tab.
3. In the **Event Listeners** dropdown, select **Kafka Event Listener** and click **Save** to activate it.

### Viewing Published Events

After activation, you can monitor the published events using Kafka UI
at [http://localhost:8081](http://localhost:8081) or via the Kafka CLI:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
  --bootstrap-server kafka:9092 \
  --topic keycloak.events.user.login \
  --from-beginning
```
