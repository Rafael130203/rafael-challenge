package com.rafael.challenge.receiver.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WebhookEventRequest {
    @NotBlank
    private String event;
    @NotBlank
    private String orderId;
    @NotBlank
    private String storeId;
    @NotBlank
    private String timestamp;
}
