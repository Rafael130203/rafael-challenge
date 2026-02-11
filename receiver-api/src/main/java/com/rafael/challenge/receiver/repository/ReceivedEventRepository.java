package com.rafael.challenge.receiver.repository;

import com.rafael.challenge.receiver.model.ReceivedEvent;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReceivedEventRepository extends MongoRepository<ReceivedEvent, String> {

    List<ReceivedEvent> findByStoreId(String storeId);

    List<ReceivedEvent> findByOrderId(String orderId);
}
