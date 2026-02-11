package com.rafael.challenge.receiver.client;

import com.rafael.challenge.receiver.model.OrderSnapshot;

public interface MarketplaceClient {
    OrderSnapshot getOrder(String orderId);
}
