package com.example.banquet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.banquet.entity.EventUser;

public interface EventUserRepository extends JpaRepository<EventUser, Integer> {
    EventUser findByEventIdAndUserId(Integer eventId, Integer userId);
}
