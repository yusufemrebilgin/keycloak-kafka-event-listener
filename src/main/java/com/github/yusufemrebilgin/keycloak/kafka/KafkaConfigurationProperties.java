package com.github.yusufemrebilgin.keycloak.kafka;

import org.jboss.logging.Logger;

import java.util.Properties;

final class KafkaConfigurationProperties extends Properties {

    private static final Logger logger = Logger.getLogger(KafkaConfigurationProperties.class);

    private static final String KAFKA_ENV_VARIABLE_PREFIX = "SPI_KAFKA_";

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
