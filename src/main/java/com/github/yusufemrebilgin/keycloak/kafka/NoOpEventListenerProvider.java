package com.github.yusufemrebilgin.keycloak.kafka;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

/**
 * A no-operation event listener provider that silently ignores all events.
 * <p>
 * This provider is used as a fallback when Kafka is not available or explicitly
 * disabled, allowing Keycloak to continue operating without event streaming.
 */
public class NoOpEventListenerProvider implements EventListenerProvider {

    @Override
    public void onEvent(Event event) {
        // No operation - events are silently ignored
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        // No operation - admin events are silently ignored
    }

    @Override
    public void close() {
        // No resources to clean up
    }

}
