package com.example.molnbaseradjavaapplikation.controller;

import com.example.molnbaseradjavaapplikation.model.Product;
import com.example.molnbaseradjavaapplikation.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getProductsAsJson() {
        return productService.getAllProducts();
    }

    @PostMapping("/products/fetch")
    public String fetchAndSaveProducts() {
        productService.fetchAndSaveProducts();
        return "redirect:/products";
    }
}