package com.example.books.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.books.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthorId(Integer authorId);

    List<Book> findByCategoryId(Integer categoryId);
}
