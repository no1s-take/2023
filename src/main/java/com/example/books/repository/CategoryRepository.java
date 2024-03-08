package com.example.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.books.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
