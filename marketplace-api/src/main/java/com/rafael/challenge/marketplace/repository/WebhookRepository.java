package com.rafael.challenge.marketplace.repository;

import com.rafael.challenge.marketplace.model.Webhook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WebhookRepository extends MongoRepository<Webhook, String> {
    List<Webhook> findByStoreIdsContaining(String storeId);
}
