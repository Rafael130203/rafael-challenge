package com.rafael.challenge.marketplace.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "webhooks")
public class Webhook {
    @Id
    private String id;
    private List<String> storeIds;
    private String callbackUrl;
}
