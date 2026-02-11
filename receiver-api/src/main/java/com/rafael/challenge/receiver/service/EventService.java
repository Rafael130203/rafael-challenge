package com.rafael.challenge.receiver.service;

import com.rafael.challenge.receiver.client.MarketplaceClient;
import com.rafael.challenge.receiver.dto.WebhookEventRequest;
import com.rafael.challenge.receiver.model.OrderSnapshot;
import com.rafael.challenge.receiver.model.ReceivedEvent;
import com.rafael.challenge.receiver.repository.ReceivedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EventService {

    private final ReceivedEventRepository repository;
    private final MarketplaceClient marketplaceClient;

    public void processEvent(WebhookEventRequest request) {

        OrderSnapshot snapshot = marketplaceClient.getOrder(request.getOrderId());

        ReceivedEvent event = new ReceivedEvent();
        event.setEvent(request.getEvent());
        event.setOrderId(request.getOrderId());
        event.setStoreId(request.getStoreId());
        event.setReceivedAt(Instant.now());
        event.setOrderSnapshot(snapshot);

        repository.save(event);
    }
}
