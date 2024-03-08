package com.example.books.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onPrePersist() {
        setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
        setModifiedAt(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
    }

    @PreUpdate
    public void onPreUpdate() {
        setModifiedAt(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
    }
}
