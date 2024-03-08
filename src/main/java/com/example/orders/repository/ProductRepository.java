package com.example.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.orders.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
