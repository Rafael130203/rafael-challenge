package com.rafael.challenge.receiver.model;

import lombok.Data;

import java.time.Instant;

@Data
public class OrderSnapshot {
    private String id;
    private String storeId;
    private String status;
    private Instant createdAt;
}
