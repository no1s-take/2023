package com.example.banquet.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
class AbstractEntity {
    @Column
    private LocalDateTime modifiedAt;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        setModifiedAt(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
        setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
    }

    @PreUpdate
    public void onPreUpdate() {
        setModifiedAt(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
    }
}
