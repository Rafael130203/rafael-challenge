package com.rafael.challenge.marketplace.controller;

import com.rafael.challenge.marketplace.dto.CreateWebhookRequest;
import com.rafael.challenge.marketplace.model.Webhook;
import com.rafael.challenge.marketplace.repository.WebhookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookRepository webhookRepository;

    @PostMapping
    public Webhook createWebhook(@RequestBody @Valid CreateWebhookRequest request) {
        Webhook webhook = new Webhook();
        webhook.setStoreIds(request.getStoreIds());
        webhook.setCallbackUrl(request.getCallbackUrl());
        return webhookRepository.save(webhook);
    }
}
