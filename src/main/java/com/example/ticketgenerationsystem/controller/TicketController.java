package com.example.ticketgenerationsystem.controller;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.PageableConvertor;
import com.example.ticketgenerationsystem.convertor.TicketConvertor;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.request.TicketGenerateRequest;
import com.example.ticketgenerationsystem.request.TicketUpdateRequest;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import com.example.ticketgenerationsystem.response.ResponseBean;
import com.example.ticketgenerationsystem.response.TicketObject;
import com.example.ticketgenerationsystem.service.AuthorizationService;
import com.example.ticketgenerationsystem.service.TicketService;
import com.example.ticketgenerationsystem.validator.TicketValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@Log4j2
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private AuthorizationService authService;

    @PostMapping
    public ResponseEntity<ResponseBean<Object>> generate(@RequestBody TicketGenerateRequest request, @RequestHeader("Authorization") String token)  {
        User user = authService.authorize(token);
        if(user == null || user.getUserType() != Constants.roleMap.get(Constants.ROLE_OPERATOR)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        TicketValidator.validate(request);
        ticketService.add(request, user);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<Object>> update(@RequestBody TicketUpdateRequest request, @PathVariable("id") int ticketId, @RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null || user.getUserType() != Constants.roleMap.get(Constants.ROLE_OPERATOR)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ticketService.update(request, ticketId, user);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @GetMapping("/closed")
    public ResponseEntity<PageableListResponse<TicketObject>> getAllClosedTickets(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(TicketConvertor.convert(ticketService.findAllWithStatus(Constants.ticketStatusMap.get(Constants.TICKET_STATUS_CLOSED), PageableConvertor.getPageableObject(pageNo, pageSize))),HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<PageableListResponse<TicketObject>> getAllActiveTickets(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(TicketConvertor.convert(ticketService.findAllWithStatus(Constants.ticketStatusMap.get(Constants.TICKET_STATUS_ACTIVE), PageableConvertor.getPageableObject(pageNo, pageSize))),HttpStatus.OK);
    }
}