package com.rafael.challenge.receiver.controller;

import com.rafael.challenge.receiver.model.ReceivedEvent;
import com.rafael.challenge.receiver.repository.ReceivedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventQueryController {

    private final ReceivedEventRepository repository;

    @GetMapping
    public List<ReceivedEvent> listAll() {
        return repository.findAll();
    }

    @GetMapping("/store/{storeId}")
    public List<ReceivedEvent> findByStore(@PathVariable String storeId) {
        return repository.findByStoreId(storeId);
    }

    @GetMapping("/order/{orderId}")
    public List<ReceivedEvent> findByOrder(@PathVariable String orderId) {
        return repository.findByOrderId(orderId);
    }
}
