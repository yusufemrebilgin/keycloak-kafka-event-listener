package com.github.yusufemrebilgin.keycloak.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.OperationType;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EventFilterTest {

    @Test
    void shouldIncludeAllTypesWhenGivenEventsAndOperationsAreNull() {
        EventFilter filter = new EventFilter(null, null);
        Stream.of(EventType.values()).forEach(eventType -> assertTrue(filter.isEventIncluded(eventType)));
        Stream.of(OperationType.values()).forEach(operationType -> assertTrue(filter.isOperationIncluded(operationType)));
    }

    @Test
    void shouldIncludeAllTypesWhenGivenEventsAndOperationsAreEmpty() {
        EventFilter filter = new EventFilter(Collections.emptySet(), Collections.emptySet());
        Stream.of(EventType.values()).forEach(eventType -> assertTrue(filter.isEventIncluded(eventType)));
        Stream.of(OperationType.values()).forEach(operationType -> assertTrue(filter.isOperationIncluded(operationType)));
    }

    @Test
    void shouldIncludeOnlySpecificEventAndOperationTypes() {
        // Arrange & Act
        Set<String> eventTypeNames = Set.of("login", "logout");
        Set<String> operationTypeNames = Set.of("create", "update");
        EventFilter filter = new EventFilter(eventTypeNames, operationTypeNames);
        // Assert
        assertTrue(filter.isEventIncluded(EventType.LOGIN));
        assertTrue(filter.isEventIncluded(EventType.LOGOUT));
        assertFalse(filter.isEventIncluded(EventType.REGISTER));
        assertTrue(filter.isOperationIncluded(OperationType.CREATE));
        assertTrue(filter.isOperationIncluded(OperationType.UPDATE));
        assertFalse(filter.isOperationIncluded(OperationType.DELETE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"login", "LOGOUT", "reGiStEr"})
    void shouldParseGivenEventTypesCaseInsensitively(String eventTypeName) {
        // Arrange & Act
        EventFilter filter = new EventFilter(Set.of(eventTypeName), Collections.emptySet());
        // Assert
        EventType expectedType = EventType.valueOf(eventTypeName.toUpperCase());
        assertTrue(filter.isEventIncluded(expectedType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "uPDaTe", "DELETE"})
    void shouldParseGivenOperationTypesCaseInsensitively(String operationTypeName) {
        // Arrange & Act
        EventFilter filter = new EventFilter(Collections.emptySet(), Set.of(operationTypeName));
        // Assert
        OperationType expectedType = OperationType.valueOf(operationTypeName.toUpperCase());
        assertTrue(filter.isOperationIncluded(expectedType));
    }

    @Test
    void shouldThrowExceptionWhenGivenEventTypeIsInvalid() {
        // Arrange
        Set<String> invalidEventType = Set.of("INVALID_EVENT_TYPE");

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new EventFilter(invalidEventType, Collections.emptySet())
        );

        assertNotNull(ex);
        assertTrue(ex.getMessage().contains("Invalid event type: 'INVALID_EVENT_TYPE'"));
    }

    @Test
    void shouldThrowExceptionWhenGivenOperationTypeIsInvalid() {
        // Arrange
        Set<String> invalidOperationTypes = Set.of("INVALID_OPERATION_TYPE");

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new EventFilter(Collections.emptySet(), invalidOperationTypes)
        );

        assertNotNull(ex);
        assertTrue(ex.getMessage().contains("Invalid operation type: 'INVALID_OPERATION_TYPE'"));
    }

    @Test
    void shouldThrowExceptionWhenEventTypesAreMixOfValidAndInvalidTypes() {
        // Arrange
        Set<String> eventTypes = Set.of("login", "logout", "invalid_event_type");

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new EventFilter(eventTypes, Collections.emptySet())
        );

        assertNotNull(ex);
    }

    @Test
    void shouldThrowExceptionWhenOperationTypesAreMixOfValidAndInvalidTypes() {
        // Arrange
        Set<String> operationTypes = Set.of("create", "update", "invalid_operation_type");

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new EventFilter(Collections.emptySet(), operationTypes)
        );

        assertNotNull(ex);
    }

}