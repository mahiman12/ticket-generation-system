package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.entity.Session;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.SessionRepo;
import com.example.ticketgenerationsystem.request.LoginRequest;
import com.example.ticketgenerationsystem.dto.TokenDTO;
import com.example.ticketgenerationsystem.util.HashGenerator;
import com.example.ticketgenerationsystem.util.UuidGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
public class AuthenticationService {
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private UserService userService;
    @Value("${token.expiry-time:10}")
    private String expiryTime;

    public TokenDTO login(LoginRequest request) {
        try {
            User user = userService.findByEmailId(request.getEmailId().toLowerCase());
            if (user == null) {
                throw new ApiException("400", "Email Id or Password Incorrect");
            }
            if (!HashGenerator.getMd5(request.getPassword()).equals(user.getPassword())) {
                throw new ApiException("400", "Email Id or Password Incorrect");
            }
            Optional<Session> sessionOptional = sessionRepo.findByUser(user);
            if (sessionOptional.isPresent()) {
                Session session = sessionOptional.get();
                TokenDTO tokenDTO = new TokenDTO();
                tokenDTO.setToken(session.getUuid());

                if (session.getExpiryAt().isAfter(LocalDateTime.now())) {
                    session.setExpiryAt(LocalDateTime.now().plusMinutes(Integer.parseInt(expiryTime)));
                    sessionRepo.save(session);
                    return tokenDTO;
                }
                sessionRepo.delete(session);
            }
            Session session = new Session();
            String uuid = HashGenerator.getMd5(UuidGenerator.generate());
            session.setUuid(uuid);
            session.setUser(user);
            session.setExpiryAt(LocalDateTime.now().plusMinutes(Integer.parseInt(expiryTime)));
            sessionRepo.save(session);

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(uuid);
            return tokenDTO;
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public void logout(TokenDTO request) {
        try {
            Optional<Session> sessionOptional = sessionRepo.findByUuid(request.getToken());
            if(!sessionOptional.isPresent()) {
                return;
            }
            sessionRepo.delete(sessionOptional.get());
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }
}
