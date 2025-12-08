package com.github.yusufemrebilgin.keycloak.kafka;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConfigurationPropertiesTest {

    @Test
    void shouldProvideDefaultKafkaPropertiesWhenNoEnvOverrides() {
        // Arrange & Act
        KafkaConfigurationProperties props = KafkaConfigurationProperties.loadFromEnv();

        // Assert
        String expectedSerializerClass = "org.apache.kafka.common.serialization.StringSerializer";
        assertEquals("localhost:9092", props.getProperty(ProducerConfigProperties.BOOTSTRAP_SERVERS.getKey()));
        assertEquals("keycloak-event-listener", props.getProperty(ProducerConfigProperties.CLIENT_ID.getKey()));
        assertEquals("keycloak.events", props.getProperty(ProducerConfigProperties.TOPIC_PREFIX.getKey()));
        assertEquals(expectedSerializerClass, props.getProperty(ProducerConfigProperties.KEY_SERIALIZER.getKey()));
        assertEquals(expectedSerializerClass, props.getProperty(ProducerConfigProperties.VALUE_SERIALIZER.getKey()));
    }

    @Test
    void shouldBeEmptyEventTypesWhenIncludedEventTypesEnvNotSet() {
        // Arrange & Act
        KafkaConfigurationProperties props = KafkaConfigurationProperties.loadFromEnv();
        Set<String> includedEventTypes = props.getIncludedEventTypes();
        // Assert
        assertNotNull(includedEventTypes);
        assertTrue(includedEventTypes.isEmpty());
    }

    @Test
    void shouldBeEmptyOperationTypesWhenIncludedOperationTypesEnvNotSet() {
        // Arrange & Act
        KafkaConfigurationProperties props = KafkaConfigurationProperties.loadFromEnv();
        Set<String> includedOperationTypes = props.getIncludedOperationTypes();
        // Assert
        assertNotNull(includedOperationTypes);
        assertTrue(includedOperationTypes.isEmpty());
    }

}
