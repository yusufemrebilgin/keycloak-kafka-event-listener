package com.github.yusufemrebilgin.keycloak.kafka;

import org.jboss.logging.Logger;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

final class KafkaConfigurationProperties extends Properties {

    private static final Logger logger = Logger.getLogger(KafkaConfigurationProperties.class);

    private static final String KAFKA_ENV_VARIABLE_PREFIX = "SPI_KAFKA_";
    private static final String INCLUDED_EVENT_TYPES = KAFKA_ENV_VARIABLE_PREFIX + "INCLUDED_EVENT_TYPES";
    private static final String INCLUDED_OPERATION_TYPES = KAFKA_ENV_VARIABLE_PREFIX + "INCLUDED_OPERATION_TYPES";

    private KafkaConfigurationProperties() {
        super();
        this.putAll(new DefaultKafkaProperties());
    }

    public static KafkaConfigurationProperties loadFromEnv() {
        logger.info("Loading Kafka configuration properties from environment variables");
        KafkaConfigurationProperties kafkaConfigurationProperties = new KafkaConfigurationProperties();
        for (ProducerConfigProperties property : ProducerConfigProperties.values()) {
            String envVar = System.getenv(KAFKA_ENV_VARIABLE_PREFIX + property.name());
            if (envVar != null) {
                kafkaConfigurationProperties.setProperty(property.getKey(), envVar);
            }
        }
        var bootstrapServers = kafkaConfigurationProperties.getProperty(ProducerConfigProperties.BOOTSTRAP_SERVERS.getKey());
        logger.infof("Kafka configuration loaded successfully (Bootstrap Servers: %s)", bootstrapServers);
        return kafkaConfigurationProperties;
    }

    public String getTopicPrefix() {
        return getProperty(ProducerConfigProperties.TOPIC_PREFIX.getKey());
    }

    public Set<String> getIncludedEventTypes() {
        String eventTypesEnv = System.getenv(INCLUDED_EVENT_TYPES);
        logger.debugf("Reading env var '%s': %s", INCLUDED_EVENT_TYPES, eventTypesEnv);
        return parseCommaSeparatedValues(eventTypesEnv);
    }

    public Set<String> getIncludedOperationTypes() {
        String operationTypesEnv = System.getenv(INCLUDED_OPERATION_TYPES);
        logger.debugf("Reading env var '%s': %s", INCLUDED_OPERATION_TYPES, operationTypesEnv);
        return parseCommaSeparatedValues(operationTypesEnv);
    }

    private Set<String> parseCommaSeparatedValues(String values) {
        Set<String> result = new HashSet<>();
        if (values != null && !values.isBlank()) {
            String[] types = values.split(",");
            for (String type : types) {
                String trimmed = type.trim();
                if (!trimmed.isEmpty()) {
                    result.add(trimmed);
                }
            }
        }
        return result;
    }

    /**
     * Defines default Kafka producer configuration properties.
     * <p>
     * These defaults provide the minimal settings required for a functional
     * Kafka producer, so the Keycloak event listener can run locally or in
     * environments without explicit configuration.
     */
    private static class DefaultKafkaProperties extends Properties {
        private DefaultKafkaProperties() {
            setProperty(ProducerConfigProperties.BOOTSTRAP_SERVERS.getKey(), "localhost:9092");
            setProperty(ProducerConfigProperties.CLIENT_ID.getKey(), "keycloak-event-listener");
            setProperty(ProducerConfigProperties.TOPIC_PREFIX.getKey(), "keycloak.events");
            setProperty(ProducerConfigProperties.KEY_SERIALIZER.getKey(), "org.apache.kafka.common.serialization.StringSerializer");
            setProperty(ProducerConfigProperties.VALUE_SERIALIZER.getKey(), "org.apache.kafka.common.serialization.StringSerializer");
        }
    }

}
