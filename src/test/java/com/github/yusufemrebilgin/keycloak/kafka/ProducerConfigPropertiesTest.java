package com.github.yusufemrebilgin.keycloak.kafka;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProducerConfigPropertiesTest {

    @ParameterizedTest
    @ValueSource(strings = {"bootstrap.servers", "key.serializer", "value.serializer"})
    void shouldExistsAllRequiredKafkaProperties(String requiredProperty) {
        String converted = requiredProperty.replaceAll("\\.", "_");
        var existingProperty = ProducerConfigProperties.valueOf(converted.toUpperCase());
        assertEquals(requiredProperty, existingProperty.getKey());
    }

}