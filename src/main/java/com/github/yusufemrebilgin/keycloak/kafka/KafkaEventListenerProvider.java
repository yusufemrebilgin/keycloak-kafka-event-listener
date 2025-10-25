package com.github.yusufemrebilgin.keycloak.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

import java.util.Optional;

public class KafkaEventListenerProvider implements EventListenerProvider {

    private static final Logger logger = Logger.getLogger(KafkaEventListenerProvider.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private final String topicPrefix;
    private final KafkaProducer<String, String> producer;

    public KafkaEventListenerProvider(String topicPrefix, KafkaProducer<String, String> producer) {
        this.topicPrefix = topicPrefix;
        this.producer = producer;
    }

    @Override
    public void onEvent(Event event) {
        try {
            String eventAsJson = mapper.writeValueAsString(event);
            String topic = topicPrefix + ".user." + event.getType().name().toLowerCase();
            String key = Optional.ofNullable(event.getUserId()).orElse(event.getRealmId());
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, eventAsJson);
            sendUserEventToKafka(event, producerRecord);
        } catch (JsonProcessingException ex) {
            logger.error("Error processing user event: " + event.getType(), ex);
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        try {
            String eventAsJson = mapper.writeValueAsString(event);
            String topic = topicPrefix + ".admin." + event.getOperationType().name().toLowerCase();
            String key = Optional.ofNullable(extractUserIdFromPath(event.getResourcePath())).orElse(event.getRealmId());
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, eventAsJson);
            sendAdminEventToKafka(event, producerRecord);
        } catch (JsonProcessingException ex) {
            logger.error("Error processing admin event: " + event.getOperationType(), ex);
        }
    }

    @Override
    public void close() {
        // No close operation needed
    }

    private void sendUserEventToKafka(Event event, ProducerRecord<String, String> producerRecord) {
        producer.send(producerRecord, (metadata, ex) -> {
            if (ex != null) {
                logger.errorf(
                        ex, "Failed to send event to Kafka [%s] for user %s",
                        event.getType(), event.getUserId()
                );
            } else {
                logger.debugf(
                        "Event sent successfully [type=%s, topic=%s, partition=%s, offset=%s]",
                        event.getType(), metadata.topic(), metadata.partition(), metadata.offset()
                );
            }
        });
    }

    private void sendAdminEventToKafka(AdminEvent event, ProducerRecord<String, String> producerRecord) {
        producer.send(producerRecord, (metadata, ex) -> {
            if (ex != null) {
                logger.errorf(
                        ex, "Failed to send admin event to Kafka [%s] for path %s",
                        event.getOperationType(), event.getResourcePath()
                );
            } else {
                logger.debugf(
                        "Admin event sent successfully [operation=%s, topic=%s, partition=%s, offset=%s]",
                        event.getOperationType(), metadata.topic(), metadata.partition(), metadata.offset()
                );
            }
        });
    }

    private String extractUserIdFromPath(String resourcePath) {
        if (resourcePath != null && resourcePath.contains("users/")) {
            String[] parts = resourcePath.split("users/");
            if (parts.length > 1) {
                String userPart = parts[1];
                int slashIndex = userPart.indexOf('/');
                return slashIndex > 0 ? userPart.substring(0, slashIndex) : userPart;
            }
        }
        return null;
    }

}
