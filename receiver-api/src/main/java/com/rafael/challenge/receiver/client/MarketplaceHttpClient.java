package com.rafael.challenge.receiver.client;

import com.rafael.challenge.receiver.model.OrderSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class MarketplaceHttpClient implements MarketplaceClient {

    private final WebClient webClient;

    @Value("${marketplace.base-url}")
    private String baseUrl;

    @Override
    public OrderSnapshot getOrder(String orderId) {
        return webClient.get()
                .uri(baseUrl + "/orders/{id}", orderId)
                .retrieve()
                .bodyToMono(OrderSnapshot.class)
                .block();
    }
}
