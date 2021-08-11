package com.example.ticketgenerationsystem.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
@Data
@SQLDelete(sql = "update sessions set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Session extends AbstractEntity<Integer> {

    @Column(unique = true, nullable = false)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expiry_at", nullable = false)
    private LocalDateTime expiryAt;
}
