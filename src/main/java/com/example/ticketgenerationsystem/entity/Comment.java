package com.example.ticketgenerationsystem.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@SQLDelete(sql = "update comments set deleted = true where id = ?")
public class Comment extends AbstractEntity<Integer> {

    @Column(columnDefinition="TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
