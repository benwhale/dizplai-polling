package com.dizplai.polling.model;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class BaseEntity {

    /**
     * The timestamp when the entity was created.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * The timestamp when the entity was last updated.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
