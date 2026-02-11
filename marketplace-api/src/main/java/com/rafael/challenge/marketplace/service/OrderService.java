package com.rafael.challenge.marketplace.service;

import com.rafael.challenge.marketplace.model.Order;
import com.rafael.challenge.marketplace.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebhookDispatcherService webhookDispatcherService;

    public Order createOrder(String storeId) {
        Order order = new Order();
        order.setStoreId(storeId);
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());

        Order saved = orderRepository.save(order);

        webhookDispatcherService.dispatchEvent("order.created", saved);

        return saved;
    }

    public Order getOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order updateStatus(String id, String status) {
        Order order = getOrder(id);
        order.setStatus(status);

        Order updated = orderRepository.save(order);

        if ("PAID".equalsIgnoreCase(status)) {
            webhookDispatcherService.dispatchEvent("order.paid", updated);
        }

        return updated;
    }
}
