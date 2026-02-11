package com.rafael.challenge.marketplace.repository;

import com.rafael.challenge.marketplace.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
