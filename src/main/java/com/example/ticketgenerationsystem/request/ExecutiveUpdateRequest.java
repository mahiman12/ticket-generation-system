package com.example.ticketgenerationsystem.request;

import lombok.Data;

@Data
public class ExecutiveUpdateRequest {
    private Integer executiveId;
    private Integer role;
    private String newPassword;
    private String newMobileNo;
    private double[] newLocation;
}
