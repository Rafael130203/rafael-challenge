package com.rafael.challenge.marketplace.service;

import com.rafael.challenge.marketplace.model.Order;
import com.rafael.challenge.marketplace.model.Webhook;
import com.rafael.challenge.marketplace.repository.WebhookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebhookDispatcherService {

    private final WebhookRepository webhookRepository;
    private final WebClient webClient = WebClient.create();

    public void dispatchEvent(String event, Order order) {
        List<Webhook> webhooks = webhookRepository.findByStoreIdsContaining(order.getStoreId());

        for (Webhook webhook : webhooks) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("event", event);
            payload.put("orderId", order.getId());
            payload.put("storeId", order.getStoreId());
            payload.put("timestamp", Instant.now().toString());

            webClient.post()
                    .uri(webhook.getCallbackUrl())
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe(); // async fire-and-forget
        }
    }
}
