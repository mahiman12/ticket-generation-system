package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.TicketConvertor;
import com.example.ticketgenerationsystem.entity.Executive;
import com.example.ticketgenerationsystem.entity.Ticket;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.entity.Vehicle;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.TicketRepo;
import com.example.ticketgenerationsystem.request.TicketGenerateRequest;
import com.example.ticketgenerationsystem.request.TicketUpdateRequest;
import com.example.ticketgenerationsystem.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ExecutiveService executiveService;

    public Ticket add(TicketGenerateRequest request, User user) {
        try {
            Vehicle vehicle = vehicleService.findByRegNo(request.getRegNo());
            if(vehicle.getOperator().getUser() != user) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            if(!request.getIssueType().equals(Constants.issueTypeMap.get(Constants.ISSUE_TYPE_GROUND_VISIT_REQUIRED))) {
                return ticketRepo.save(TicketConvertor.convert(request, Constants.roleMap.get(Constants.TICKET_STATUS_ACTIVE), vehicle, null));
            }
            double minDist = Double.MAX_VALUE;
            Executive assignedExecutive = null;
            List<Executive> executiveList = executiveService.findAll(Constants.roleMap.get(Constants.ROLE_EXECUTIVE));
            for(Executive executive: executiveList) {
                minDist = Math.min(minDist, Util.calcDistance(request.getLocation()[0], request.getLocation()[1], executive.getLocation()[0], executive.getLocation()[1]));
                assignedExecutive = executive;
            }
            return ticketRepo.save(TicketConvertor.convert(request, Constants.roleMap.get(Constants.TICKET_STATUS_ACTIVE), vehicle, assignedExecutive));
        } catch(DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public Ticket findById(int ticketId) {
        try {
            Optional<Ticket> ticketOptional = ticketRepo.findById(ticketId);
            return ticketOptional.orElse(null);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public Ticket update(TicketUpdateRequest request, int ticketId, User user) {
        try {
            Optional<Ticket> ticketOptional = ticketRepo.findById(ticketId);
            if(!ticketOptional.isPresent()) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            Ticket ticket = ticketOptional.get();
            if(ticket.getVehicle().getOperator().getUser() != user || request.getStatus() == null || !request.getStatus().equals(Constants.ticketStatusMap.get(Constants.TICKET_STATUS_CLOSED))) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            ticket.setStatus(request.getStatus());
            ticket = ticketRepo.save(ticket);
            return ticket;
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public Page<Ticket> findAllWithStatus(int status, Pageable pageable) {
        try {
            return ticketRepo.findAllByStatus(status, pageable);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public void deleteAllByVehicle(Vehicle vehicle) {
        try{
            ticketRepo.deleteAllByVehicle(vehicle);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }
}
