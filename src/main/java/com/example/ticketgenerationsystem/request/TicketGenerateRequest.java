package com.example.ticketgenerationsystem.request;

import lombok.Data;

@Data
public class TicketGenerateRequest {
    private Integer issueType;
    private String issueDescription;
    private double[] location;
    private String regNo;
}
