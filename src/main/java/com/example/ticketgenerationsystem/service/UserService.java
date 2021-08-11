package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User add(User user)  {
        try {
            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public Optional<User> findById(int userId)  {
        try {
            return userRepo.findById(userId);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public User findByEmailId(String emailId) {
        try {
            Optional<User> userOptional = userRepo.findByEmailId(emailId);
            return userOptional.orElse(null);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public void delete(int userId)  {

        try {
            userRepo.deleteById(userId);
        } catch (Exception e) {
            throw new ApiException("400", e.getMessage());
        }
    }

    public List<User> findAllByRole(int role)  {
        try {
            return userRepo.findAllByUserType(role);
        } catch (Exception e) {
            throw new ApiException("400", e.getMessage());
        }
    }

    public User update(User user)  {
        try {
            return userRepo.save(user);
        } catch(Exception e) {
            throw new ApiException("400", e.getMessage());
        }
    }
}
