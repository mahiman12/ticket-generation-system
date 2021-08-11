package com.example.ticketgenerationsystem.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class AbstractEntity<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    T id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;

    public AbstractEntity<T> setId(T id) {
        this.id = id;
        return this;
    }

    public AbstractEntity<T> setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AbstractEntity<T> setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public AbstractEntity<T> setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime currTime = LocalDateTime.now();
        if (null == this.createdAt) {
            this.createdAt = currTime;
        }
        if (null == this.updatedAt) {
            this.updatedAt = currTime;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
