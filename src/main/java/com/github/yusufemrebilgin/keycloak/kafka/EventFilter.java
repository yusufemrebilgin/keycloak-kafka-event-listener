package com.github.yusufemrebilgin.keycloak.kafka;

import org.keycloak.events.EventType;
import org.keycloak.events.admin.OperationType;
import org.keycloak.util.EnumWithStableIndex;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventFilter {

    private final Set<EventType> includedEventTypes;
    private final Set<OperationType> includedOperationTypes;

    public EventFilter(Set<String> eventTypeNames, Set<String> operationTypeNames) {
        this.includedEventTypes = parseAndValidateEventTypes(eventTypeNames);
        this.includedOperationTypes = parseAndValidateOperationTypes(operationTypeNames);
    }

    private Set<EventType> parseAndValidateEventTypes(Set<String> eventTypeNames) {
        if (eventTypeNames == null || eventTypeNames.isEmpty()) {
            return EnumSet.allOf(EventType.class);
        }

        return eventTypeNames.stream()
                .map(this::parseEventType)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(EventType.class)));
    }

    private EventType parseEventType(String eventTypeName) {
        try {
            return EventType.valueOf(eventTypeName.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(String.format(
                    "Invalid event type: '%s' valid types are [%s]",
                    eventTypeName, getValidTypes(EventType::values)), ex);
        }
    }

    public boolean isEventIncluded(EventType eventType) {
        return includedEventTypes.contains(eventType);
    }

    private Set<OperationType> parseAndValidateOperationTypes(Set<String> operationTypeNames) {
        if (operationTypeNames == null || operationTypeNames.isEmpty()) {
            return EnumSet.allOf(OperationType.class);
        }

        return operationTypeNames.stream()
                .map(this::parseOperationType)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(OperationType.class)));
    }

    private OperationType parseOperationType(String operationTypeName) {
        try {
            return OperationType.valueOf(operationTypeName.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(String.format(
                    "Invalid operation type: '%s' valid types are [%s]",
                    operationTypeName, getValidTypes(OperationType::values)), ex);
        }
    }

    public boolean isOperationIncluded(OperationType operationType) {
        return includedOperationTypes.contains(operationType);
    }

    private String getValidTypes(Supplier<? extends Enum<? extends EnumWithStableIndex>[]> enumValueSupplier) {
        return Stream.of(enumValueSupplier.get()).map(Enum::name).collect(Collectors.joining(", "));
    }

}
