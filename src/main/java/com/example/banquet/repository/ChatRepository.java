package com.example.banquet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.banquet.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findAllByOrderByCreatedAt();
}
