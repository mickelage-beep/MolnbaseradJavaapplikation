package com.example.molnbaseradjavaapplikation.controller;

import com.example.molnbaseradjavaapplikation.dto.CreateOrderRequest;
import com.example.molnbaseradjavaapplikation.dto.OrderResponse;
import com.example.molnbaseradjavaapplikation.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}