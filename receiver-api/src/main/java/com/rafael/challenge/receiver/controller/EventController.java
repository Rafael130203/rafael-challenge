package com.rafael.challenge.receiver.controller;

import com.rafael.challenge.receiver.dto.WebhookEventRequest;
import com.rafael.challenge.receiver.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public void receiveEvent(@RequestBody @Valid WebhookEventRequest request) {
        eventService.processEvent(request);
    }
}
