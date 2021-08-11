package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.entity.Session;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.SessionRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
public class AuthorizationService {
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private UserService userService;

    public User authorize(String tokenHeader) {
        try {
            if (tokenHeader.length() <= Constants.AUTHORIZATION_HEADER_PREFIX.length() + 2 || !tokenHeader.substring(0, Constants.AUTHORIZATION_HEADER_PREFIX.length()).equals("Bearer")) {
                return null;
            }
            String token = tokenHeader.substring(Constants.AUTHORIZATION_HEADER_PREFIX.length()+1);
            Optional<Session> sessionOptional = sessionRepo.findByUuid(token);

            if(!sessionOptional.isPresent() || LocalDateTime.now().isAfter(sessionOptional.get().getExpiryAt())) {
                return null;
            }
            Optional<User> userOptional = userService.findById(sessionOptional.get().getUser().getId());
            if(!userOptional.isPresent()) {
                sessionRepo.delete(sessionOptional.get());
                return null;
            }
            return userOptional.get();
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }
}
