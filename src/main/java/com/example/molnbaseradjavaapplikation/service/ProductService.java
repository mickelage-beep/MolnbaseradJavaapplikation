package com.example.molnbaseradjavaapplikation.service;


import com.example.molnbaseradjavaapplikation.dto.DummyJsonProductResponse;
import com.example.molnbaseradjavaapplikation.model.Product;
import com.example.molnbaseradjavaapplikation.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.restTemplate = new RestTemplate();
    }

    public List<Product> fetchAndSaveProducts() {
        //String url = "https://dummyjson.com/products";
        String url ="https://fakestoreapi.com/products";

        DummyJsonProductResponse response = restTemplate.getForObject(url, DummyJsonProductResponse.class);

        if(response == null){
            System.out.println("API returned null");
            return productRepository.findAll();
        }

        //List<Product> products = Arrays.asList(response);
        productRepository.saveAll(response.getProducts());

        return productRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
