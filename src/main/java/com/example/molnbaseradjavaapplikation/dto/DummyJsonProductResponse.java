package com.example.molnbaseradjavaapplikation.dto;

import com.example.molnbaseradjavaapplikation.model.Product;

import java.util.List;

public class DummyJsonProductResponse {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
