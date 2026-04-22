package com.example.molnbaseradjavaapplikation.controller;


import com.example.molnbaseradjavaapplikation.model.Product;
import com.example.molnbaseradjavaapplikation.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {


        private final ProductService service;

        public ProductController(ProductService service) {this.service = service;}


    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }
    @PostMapping("/fetch")
    public List<Product> fetchProducts() {
        return service.fetchAndSaveProducts();
    }



}
