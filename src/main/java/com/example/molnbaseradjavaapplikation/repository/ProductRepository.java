package com.example.molnbaseradjavaapplikation.repository;

import com.example.molnbaseradjavaapplikation.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
