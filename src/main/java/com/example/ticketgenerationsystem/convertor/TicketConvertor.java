package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.entity.Executive;
import com.example.ticketgenerationsystem.entity.Ticket;
import com.example.ticketgenerationsystem.entity.Vehicle;
import com.example.ticketgenerationsystem.request.TicketGenerateRequest;
import com.example.ticketgenerationsystem.response.ExecutiveObject;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import com.example.ticketgenerationsystem.response.TicketObject;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class TicketConvertor {
    public static Ticket convert(TicketGenerateRequest request, int status,Vehicle vehicle, Executive executive) {
        Ticket ticket = new Ticket();

        ticket.setIssueType(request.getIssueType());
        ticket.setIssueDescription(request.getIssueDescription());
        ticket.setStatus(status);
        ticket.setLocation(request.getLocation());
        ticket.setExecutive(executive);
        ticket.setVehicle(vehicle);
        return ticket;
    }

    public static PageableListResponse<TicketObject> convert(Page<Ticket> ticketPage) {
        PageableListResponse<TicketObject> ticketResponse = new PageableListResponse<>();
        List<TicketObject> ticketObjectList = new ArrayList<>();
        ticketResponse.setTotalElements(ticketPage.getTotalElements());
        ticketResponse.setTotalPages(ticketPage.getTotalPages());
        for(Ticket ticket : ticketPage.getContent()) {
            TicketObject ticketObject = new TicketObject();
            ticketObject.setLocation(ticket.getLocation());
            ticketObject.setStatus(ticket.getStatus());
            ticketObject.setIssueType(ticket.getIssueType());
            ticketObject.setIssueDescription(ticket.getIssueDescription());
            ticketObject.setVehicleRegNo(ticket.getVehicle().getRegNo());
            if(ticket.getExecutive() != null) {
                ticketObject.setExecutiveFirstName(ticket.getExecutive().getFirstName());
                ticketObject.setExecutiveEmailId(ticket.getExecutive().getUser().getEmailId());
            }
            ticketObjectList.add(ticketObject);
        }
        ticketResponse.setContent(ticketObjectList);
        return ticketResponse;
    }
}
