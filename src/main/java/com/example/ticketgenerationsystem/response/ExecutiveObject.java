package com.example.ticketgenerationsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExecutiveObject {
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String emailId;
    private double[] location;
}
