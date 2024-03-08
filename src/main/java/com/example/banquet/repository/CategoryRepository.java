package com.example.banquet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.banquet.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
