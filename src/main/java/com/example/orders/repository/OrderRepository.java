package com.example.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.orders.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
