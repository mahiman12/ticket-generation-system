package com.example.ticketgenerationsystem.request;

import lombok.Data;

import java.util.List;

@Data
public class OperatorSignupRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNo;
    private String password;
    List<VehicleAddRequest> vehicles;
}
