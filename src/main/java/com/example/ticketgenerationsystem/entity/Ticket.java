package com.example.ticketgenerationsystem.entity;

import com.vladmihalcea.hibernate.type.array.DoubleArrayType;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
@Data
@TypeDefs({
        @TypeDef(
                name = "double-array",
                typeClass = DoubleArrayType.class
        )
})
@SQLDelete(sql = "update tickets set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Ticket extends AbstractEntity<Integer> {

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "issue_type", nullable = false)
    private int issueType;

    @Column(name = "issue_description",columnDefinition="TEXT")
    private String issueDescription;

    @Type(type = "double-array")
    @Column(name = "location", columnDefinition = "float[]", nullable = false)
    private double[] location;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "executive_id")
    private Executive executive;
}
