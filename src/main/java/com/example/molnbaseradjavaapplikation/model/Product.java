package com.example.molnbaseradjavaapplikation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    private Long id;

    private String title;
    private Double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(length = 1000)
    private String image;
}