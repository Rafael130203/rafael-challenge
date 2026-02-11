package com.rafael.challenge.receiver.service;

import com.rafael.challenge.receiver.client.MarketplaceClient;
import com.rafael.challenge.receiver.dto.WebhookEventRequest;
import com.rafael.challenge.receiver.model.OrderSnapshot;
import com.rafael.challenge.receiver.repository.ReceivedEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private ReceivedEventRepository repository;

    @Mock
    private MarketplaceClient marketplaceClient;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldSaveReceivedEvent() {
        WebhookEventRequest request = new WebhookEventRequest();
        request.setEvent("order.paid");
        request.setOrderId("order-1");
        request.setStoreId("store-1");
        request.setTimestamp(Instant.now().toString());

        OrderSnapshot snapshot = new OrderSnapshot();
        snapshot.setId("order-1");
        snapshot.setStoreId("store-1");
        snapshot.setStatus("PAID");
        snapshot.setCreatedAt(Instant.now());

        when(marketplaceClient.getOrder("order-1")).thenReturn(snapshot);

        eventService.processEvent(request);

        verify(repository, times(1)).save(any());
    }
}
