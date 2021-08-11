package com.example.ticketgenerationsystem.entity;

import com.vladmihalcea.hibernate.type.array.DoubleArrayType;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
import org.hibernate.annotations.*;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
//import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "executives")
@Data
@TypeDefs({
        @TypeDef(
                name = "double-array",
                typeClass = DoubleArrayType.class
        )
})
@SQLDelete(sql = "update executives set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Executive extends AbstractEntity<Integer>{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_no", length = 10, nullable = false, unique = true)
    private String mobileNo;

    @Type(type = "double-array")
    @Column(name = "location", columnDefinition = "float[]", nullable = false)
    private double[] location;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
