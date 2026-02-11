package com.rafael.challenge.marketplace.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String storeId;
    private String status;
    private Instant createdAt;
}
