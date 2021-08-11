package com.example.ticketgenerationsystem.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "operators")
@Data
@SQLDelete(sql = "update operators set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Operator extends AbstractEntity<Integer> {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_no", length = 10, unique = true, nullable = false)
    private String mobileNo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
