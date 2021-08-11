package com.example.ticketgenerationsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TicketObject {
    private Integer status;
    private Integer issueType;
    private String issueDescription;
    private double[] location;
    @JsonProperty("regNo")
    private String vehicleRegNo;
    private String executiveFirstName;
    private String executiveEmailId;
}
