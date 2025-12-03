package com.github.yusufemrebilgin.keycloak.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NoOpEventListenerProviderTest {

    private NoOpEventListenerProvider provider;

    @BeforeEach
    void setUpProvider() {
        provider = new NoOpEventListenerProvider();
    }

    @Test
    void shouldSilentlyIgnoreUserEventWithoutThrowingException() {
        assertDoesNotThrow(() -> provider.onEvent(new Event()));
    }

    @Test
    void shouldHandleNullUserEventWithoutThrowingException() {
        assertDoesNotThrow(() -> provider.onEvent(null));
    }

    @Test
    void shouldSilentlyIgnoreAdminEventWithoutThrowingException() {
        assertDoesNotThrow(() -> provider.onEvent(new AdminEvent(), true));
        assertDoesNotThrow(() -> provider.onEvent(new AdminEvent(), false));
    }

    @Test
    void shouldHandleNullAdminEventWithoutThrowingException() {
        assertDoesNotThrow(() -> provider.onEvent(null, true));
        assertDoesNotThrow(() -> provider.onEvent(null, false));
    }

    @Test
    void shouldCloseWithoutThrowingException() {
        assertDoesNotThrow(() -> provider.close());
    }

}