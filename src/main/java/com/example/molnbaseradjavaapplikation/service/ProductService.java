package com.example.molnbaseradjavaapplikation.service;

import com.example.molnbaseradjavaapplikation.dto.ProductDTO;
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
        String url = "http://yahyatesting-env.eba-sarnymwd.eu-north-1.elasticbeanstalk.com/products";
                //"https://fakestoreapi.com/products";

        ProductDTO[] response = restTemplate.getForObject(url, ProductDTO[].class);

        if (response == null) {
            System.out.println("API returned null");
            return productRepository.findAll();
        }

        List<Product> products = Arrays.stream(response)
                .map(this::mapToProduct)
                .toList();

        productRepository.saveAll(products);

        return productRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private Product mapToProduct(ProductDTO dto) {
        Product product = new Product();

        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setPrice(Double.valueOf(dto.getPrice()));
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setImage(dto.getImage());

        return product;
    }
}