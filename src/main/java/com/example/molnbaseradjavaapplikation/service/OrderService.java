package com.example.molnbaseradjavaapplikation.service;

import com.example.molnbaseradjavaapplikation.dto.CreateOrderRequest;
import com.example.molnbaseradjavaapplikation.dto.OrderItemRequest;
import com.example.molnbaseradjavaapplikation.dto.OrderResponse;
import com.example.molnbaseradjavaapplikation.model.Order;
import com.example.molnbaseradjavaapplikation.model.OrderItem;
import com.example.molnbaseradjavaapplikation.model.Product;
import com.example.molnbaseradjavaapplikation.model.Users;
import com.example.molnbaseradjavaapplikation.repository.OrderRepository;
import com.example.molnbaseradjavaapplikation.repository.ProductRepository;
import com.example.molnbaseradjavaapplikation.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Order must contain at least one product");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

            BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
            BigDecimal quantity = BigDecimal.valueOf(itemRequest.getQuantity());

            totalPrice = totalPrice.add(productPrice.multiply(quantity));
        }

        Order order = new Order(user, totalPrice);

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

            BigDecimal priceAtPurchase = BigDecimal.valueOf(product.getPrice());

            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    itemRequest.getQuantity(),
                    priceAtPurchase
            );

            order.addItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getUser().getUsername(),
                savedOrder.getTotalPrice(),
                savedOrder.getCreatedAt(),
                "Order created successfully"
        );
    }
}