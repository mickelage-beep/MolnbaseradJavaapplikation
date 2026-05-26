package com.example.molnbaseradjavaapplikation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

    private Long orderId;
    private String username;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private String message;

    public OrderResponse(Long orderId, String username, BigDecimal totalPrice, LocalDateTime createdAt, String message) {
        this.orderId = orderId;
        this.username = username;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
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

    public String getMessage() {
        return message;
    }
}