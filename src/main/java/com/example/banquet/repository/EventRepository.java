package com.example.banquet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.banquet.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByCategoryId(Integer categoryId);

    List<Event> findByUserId(Integer UserId);
}
