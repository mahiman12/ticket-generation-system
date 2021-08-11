package com.example.ticketgenerationsystem.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@Data
@SQLDelete(sql = "update vehicles set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Vehicle extends AbstractEntity<Integer> {

    @Column(name = "reg_no", length = 10, nullable = false, unique = true)
    private String regNo;

    @Column(name = "model", nullable = false)
    private String model;

    @ManyToOne()
    @JoinColumn(name="operator_id", nullable = false)
    private Operator operator;
}
