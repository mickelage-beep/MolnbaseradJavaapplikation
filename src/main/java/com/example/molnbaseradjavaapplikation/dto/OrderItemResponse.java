package com.example.molnbaseradjavaapplikation.dto;

import java.math.BigDecimal;

public class OrderItemResponse {

    private String productTitle;
    private String image;
    private Integer quantity;
    private BigDecimal priceAtPurchase;

    public OrderItemResponse(String productTitle, String image, Integer quantity, BigDecimal priceAtPurchase) {
        this.productTitle = productTitle;
        this.image = image;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public String getProductTitle() { return productTitle; }
    public String getImage() { return image; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
}
