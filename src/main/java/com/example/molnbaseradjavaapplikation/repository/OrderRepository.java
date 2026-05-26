package com.example.molnbaseradjavaapplikation.repository;

import com.example.molnbaseradjavaapplikation.model.Order;
import com.example.molnbaseradjavaapplikation.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(Users user);
}