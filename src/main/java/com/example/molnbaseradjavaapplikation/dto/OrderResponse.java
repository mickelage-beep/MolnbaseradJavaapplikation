package com.example.molnbaseradjavaapplikation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long orderId;
    private String username;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
    private String message;

    public OrderResponse(Long orderId,
                         String username,
                         BigDecimal totalPrice,
                         LocalDateTime createdAt,
                         List<OrderItemResponse> items,
                         String message) {
        this.orderId = orderId;
        this.username = username;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.items = items;
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public String getMessage() {
        return message;
    }
}