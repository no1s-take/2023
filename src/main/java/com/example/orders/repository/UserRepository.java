package com.example.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.orders.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
