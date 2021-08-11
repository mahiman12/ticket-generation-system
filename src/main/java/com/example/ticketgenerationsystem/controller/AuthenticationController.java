package com.example.ticketgenerationsystem.controller;

import com.example.ticketgenerationsystem.request.LoginRequest;
import com.example.ticketgenerationsystem.dto.TokenDTO;
import com.example.ticketgenerationsystem.response.ResponseBean;
import com.example.ticketgenerationsystem.service.AuthenticationService;
import com.example.ticketgenerationsystem.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) {
        LoginValidator.validate(request);
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @DeleteMapping("logout")
    public ResponseEntity<ResponseBean<Object>> logout(@RequestBody TokenDTO request) {
        LoginValidator.validate(request);
        authService.logout(request);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }
}
