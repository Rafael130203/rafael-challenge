package com.rafael.challenge.receiver.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class ReceivedEvent {
    @Id
    private String id;
    private String event;
    private String orderId;
    private String storeId;
    private Instant receivedAt;
    private OrderSnapshot orderSnapshot;
}
