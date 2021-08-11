package com.example.ticketgenerationsystem.request;

import lombok.Data;

@Data
public class ExecutiveSignupRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNo;
    private String password;
    private double[] location;
}
