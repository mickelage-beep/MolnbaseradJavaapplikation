package com.example.molnbaseradjavaapplikation.dto;

import java.util.List;

public class CreateOrderRequest {

    private String username;
    private List<OrderItemRequest> items;

    public CreateOrderRequest() {
    }

    public String getUsername() {
        return username;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}