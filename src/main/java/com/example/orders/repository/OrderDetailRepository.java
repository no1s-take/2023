package com.example.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.orders.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
