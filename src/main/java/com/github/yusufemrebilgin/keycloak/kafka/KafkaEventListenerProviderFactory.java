package com.github.yusufemrebilgin.keycloak.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.Set;

public class KafkaEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final Logger logger = Logger.getLogger(KafkaEventListenerProviderFactory.class);

    private static final String KAFKA_LISTENER_PROVIDER_ID = "kafka-event-listener";

    private boolean kafkaAvailable;
    private KafkaProducer<String, String> producer;
    private KafkaConfigurationProperties kafkaConfigurationProperties;
    private EventFilter eventFilter;

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        if (!kafkaAvailable) {
            return new NoOpEventListenerProvider();
        }
        String topicPrefix = kafkaConfigurationProperties.getTopicPrefix();
        return new KafkaEventListenerProvider(topicPrefix, producer, eventFilter);
    }

    @Override
    public void init(Config.Scope scope) {
        logger.info("Initializing [" + KafkaEventListenerProvider.class.getSimpleName() + "]");
        String kafkaEnabled = System.getenv("SPI_KAFKA_ENABLED");
        if (kafkaEnabled == null || kafkaEnabled.equalsIgnoreCase("false")) {
            logger.info("Kafka event listener is disabled; skipping initialization...");
            return;
        }

        try {
            kafkaConfigurationProperties = KafkaConfigurationProperties.loadFromEnv();
            producer = new KafkaProducer<>(kafkaConfigurationProperties);
            Set<String> includedEventTypes = kafkaConfigurationProperties.getIncludedEventTypes();
            Set<String> includedOperationTypes = kafkaConfigurationProperties.getIncludedOperationTypes();
            eventFilter = new EventFilter(includedEventTypes, includedOperationTypes);

            logger.info("Kafka producer initialized successfully");
            if (!includedEventTypes.isEmpty() && !includedOperationTypes.isEmpty()) {
                logger.infof(
                        "Event filtering initialized - [User Events: %d, Admin Operations: %d]",
                        includedEventTypes.size(),
                        includedOperationTypes.size()
                );
            }

            kafkaAvailable = true;
        } catch (Exception ex) {
            logger.error("Failed to initialize [" + KafkaEventListenerProvider.class.getSimpleName() + "]");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // No post-initialization needed
    }

    @Override
    public void close() {
        logger.info("Closing [" + KafkaEventListenerProviderFactory.class.getSimpleName() + "]");
        if (producer != null) {
            try {
                producer.flush();
                producer.close();
            } catch (Exception ex) {
                logger.error("Error closing Kafka producer", ex);
            }
        }
    }

    @Override
    public String getId() {
        return KAFKA_LISTENER_PROVIDER_ID;
    }

}
