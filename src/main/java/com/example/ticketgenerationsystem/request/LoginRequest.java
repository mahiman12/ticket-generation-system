package com.example.ticketgenerationsystem.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailId;
    private String password;
}
