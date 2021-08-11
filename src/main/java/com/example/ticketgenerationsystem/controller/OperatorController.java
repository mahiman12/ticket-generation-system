package com.example.ticketgenerationsystem.controller;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.dto.OperatorDTO;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.request.OperatorSignupRequest;
import com.example.ticketgenerationsystem.request.OperatorUpdateRequest;
import com.example.ticketgenerationsystem.response.ResponseBean;
import com.example.ticketgenerationsystem.service.AuthorizationService;
import com.example.ticketgenerationsystem.service.OperatorService;
import com.example.ticketgenerationsystem.validator.OperatorValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
@Log4j2
public class OperatorController {
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private AuthorizationService authService;

    @PostMapping
    public ResponseEntity<ResponseBean<Object>> signup(@RequestBody OperatorSignupRequest request) {
        OperatorValidator.validate(request); // validates operator dto as well as corresponding vehicle dto
        operatorService.add(request);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseBean<Object>> update(@RequestBody OperatorUpdateRequest request, @RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        OperatorValidator.validate(request);
        operatorService.update(request, user);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseBean<Object>> delete(@RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        operatorService.delete(user);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OperatorDTO>> getAll(@RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null || user.getUserType() != Constants.roleMap.get(Constants.ROLE_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<OperatorDTO> operatorDTOList = operatorService.findAll();
        return new ResponseEntity<>(operatorDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorDTO> getOne(@PathVariable(name = "id") int operatorId, @RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null || user.getUserType() != Constants.roleMap.get(Constants.ROLE_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        OperatorDTO operatorDTO = operatorService.findById(operatorId);
        return new ResponseEntity<>(operatorDTO, HttpStatus.OK);
    }
}
