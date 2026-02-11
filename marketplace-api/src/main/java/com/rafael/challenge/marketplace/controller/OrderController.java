package com.rafael.challenge.marketplace.controller;

import com.rafael.challenge.marketplace.dto.CreateOrderRequest;
import com.rafael.challenge.marketplace.dto.UpdateStatusRequest;
import com.rafael.challenge.marketplace.model.Order;
import com.rafael.challenge.marketplace.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody @Valid CreateOrderRequest request) {
        return orderService.createOrder(request.getStoreId());
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderService.getOrder(id);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable String id,
                              @RequestBody @Valid UpdateStatusRequest request) {
        return orderService.updateStatus(id, request.getStatus());
    }
}
