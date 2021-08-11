package com.example.ticketgenerationsystem.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@SQLDelete(sql = "update users set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class User extends AbstractEntity<Integer> {

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    /* The password field is used to store password hash of the user's password */
    @Column(name = "password", nullable = false)
    private String password;

    // Role of user
    @Column(name = "user_type", nullable = false)
    private int userType;
}
