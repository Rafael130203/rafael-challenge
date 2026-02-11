package com.rafael.challenge.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateWebhookRequest {
    @NotEmpty
    private List<String> storeIds;
    @NotBlank
    private String callbackUrl;
}
