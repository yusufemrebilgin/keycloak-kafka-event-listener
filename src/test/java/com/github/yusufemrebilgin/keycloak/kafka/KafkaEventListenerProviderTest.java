package com.github.yusufemrebilgin.keycloak.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.AuthDetails;
import org.keycloak.events.admin.OperationType;
import org.keycloak.util.EnumWithStableIndex;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEventListenerProviderTest {

    private final String testTopicPrefix = "test.events";

    @Mock
    KafkaProducer<String, String> producer;

    @Mock
    EventFilter filter;

    @Captor
    ArgumentCaptor<ProducerRecord<String, String>> producerRecordCaptor;

    private KafkaEventListenerProvider provider;

    @BeforeEach
    void setUpProvider() {
        provider = new KafkaEventListenerProvider(testTopicPrefix, producer, filter);
    }

    @Test
    void shouldNotSendUserEventWhenFilteredOut() {
        // Arrange
        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.LOGIN);
        when(filter.isEventIncluded(EventType.LOGIN)).thenReturn(false);

        // Act & Verify
        provider.onEvent(event);
        verify(producer, never()).send(any(), any());
    }

    @Test
    void shouldSendUserEventWithUserIdWhenEventIsIncluded() {
        // Arrange
        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.LOGIN);
        when(event.getUserId()).thenReturn("test-user");
        when(filter.isEventIncluded(EventType.LOGIN)).thenReturn(true);

        // Act
        provider.onEvent(event);

        // Verify & Assert
        verify(producer, times(1)).send(producerRecordCaptor.capture(), any());
        ProducerRecord<String, String> producerRecord = producerRecordCaptor.getValue();

        assertNotNull(producerRecord);
        assertNotNull(producerRecord.value());
        assertEquals("test-user", producerRecord.key());
        assertEquals(buildTopicName(EventType.LOGIN), producerRecord.topic());
    }

    @Test
    void shouldSendUserEventWithRealmIdWhenUserIdIsNullAndEventIncluded() {
        // Arrange
        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.LOGOUT);
        when(event.getUserId()).thenReturn(null);
        when(event.getRealmId()).thenReturn("test-realm");
        when(filter.isEventIncluded(EventType.LOGOUT)).thenReturn(true);

        // Act
        provider.onEvent(event);

        // Verify & Assert
        verify(producer, times(1)).send(producerRecordCaptor.capture(), any());
        ProducerRecord<String, String> producerRecord = producerRecordCaptor.getValue();

        assertNotNull(producerRecord);
        assertNotNull(producerRecord.value());
        assertEquals("test-realm", producerRecord.key());
        assertEquals(buildTopicName(EventType.LOGOUT), producerRecord.topic());
    }

    @Test
    void shouldNotSendAdminEventWhenFilteredOut() {
        // Arrange
        AdminEvent event = mock(AdminEvent.class);
        when(event.getOperationType()).thenReturn(OperationType.CREATE);
        when(filter.isOperationIncluded(OperationType.CREATE)).thenReturn(false);

        // Act & Verify
        provider.onEvent(event, true);
        verify(producer, never()).send(any(), any());
    }

    @Test
    void shouldSendAdminEventWithResourceIdWhenAdminEventIsIncluded() {
        // Arrange
        AdminEvent event = mock(AdminEvent.class);
        when(event.getOperationType()).thenReturn(OperationType.UPDATE);
        when(event.getResourceId()).thenReturn("test-resource-id");
        when(filter.isOperationIncluded(OperationType.UPDATE)).thenReturn(true);

        // Act
        provider.onEvent(event, true);

        // Verify & Assert
        verify(producer, times(1)).send(producerRecordCaptor.capture(), any());
        ProducerRecord<String, String> producerRecord = producerRecordCaptor.getValue();

        assertNotNull(producerRecord);
        assertNotNull(producerRecord.value());
        assertEquals("test-resource-id", producerRecord.key());
        assertEquals(buildTopicName(OperationType.UPDATE), producerRecord.topic());
    }

    @Test
    void shouldSendAdminEventWithUserIdWhenResourceIdIsNullAndEventIncluded() {
        // Arrange
        AdminEvent event = mock(AdminEvent.class);
        when(event.getOperationType()).thenReturn(OperationType.UPDATE);
        when(event.getResourceId()).thenReturn(null);

        AuthDetails authDetails = new AuthDetails();
        authDetails.setUserId("test-user-id");
        when(event.getAuthDetails()).thenReturn(authDetails);
        when(filter.isOperationIncluded(OperationType.UPDATE)).thenReturn(true);

        // Act
        provider.onEvent(event, true);

        // Verify & Assert
        verify(producer, times(1)).send(producerRecordCaptor.capture(), any());
        ProducerRecord<String, String> producerRecord = producerRecordCaptor.getValue();

        assertNotNull(producerRecord);
        assertNotNull(producerRecord.value());
        assertEquals("test-user-id", producerRecord.key());
        assertEquals(buildTopicName(OperationType.UPDATE), producerRecord.topic());
    }

    @Test
    void shouldSendAdminEventWithRealmIdWhenResourceIdAndAuthDetailsIsNullAndEventIncluded() {
        // Arrange
        AdminEvent event = mock(AdminEvent.class);
        when(event.getOperationType()).thenReturn(OperationType.DELETE);
        when(event.getResourceId()).thenReturn(null);
        when(event.getAuthDetails()).thenReturn(null);
        when(event.getRealmId()).thenReturn("test-realm-id");
        when(filter.isOperationIncluded(OperationType.DELETE)).thenReturn(true);

        // Act
        provider.onEvent(event, true);

        // Verify & Assert
        verify(producer, times(1)).send(producerRecordCaptor.capture(), any());
        ProducerRecord<String, String> producerRecord = producerRecordCaptor.getValue();

        assertNotNull(producerRecord);
        assertNotNull(producerRecord.value());
        assertEquals("test-realm-id", producerRecord.key());
        assertEquals(buildTopicName(OperationType.DELETE), producerRecord.topic());
    }

    // Helper Methods ---------------------------------------------------------------------------------------------

    private String buildTopicName(Enum<? extends EnumWithStableIndex> keycloakTypeEnum) {
        String mid = (keycloakTypeEnum instanceof EventType) ? ".user." : ".admin.";
        return testTopicPrefix + mid + keycloakTypeEnum.name().toLowerCase();
    }

}