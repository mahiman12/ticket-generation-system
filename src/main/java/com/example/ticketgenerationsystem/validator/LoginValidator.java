package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.request.LoginRequest;
import com.example.ticketgenerationsystem.dto.TokenDTO;

public class LoginValidator {
    public static void validate(LoginRequest request) {
        FieldValidator.validate("Email", request.getEmailId().toLowerCase(), Constants.EMAIL_REGEX);
        FieldValidator.validate("Password", request.getPassword(), Constants.PASSWORD_REGEX);
    }

    public static void validate(TokenDTO request) {
        FieldValidator.validate("Token", request.getToken(), Constants.ALPHANUMERIC_REGEX);
    }
}
