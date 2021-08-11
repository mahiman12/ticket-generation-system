package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.request.ExecutiveSignupRequest;
import com.example.ticketgenerationsystem.request.ExecutiveUpdateRequest;
import com.example.ticketgenerationsystem.request.PasswordUpdateRequest;

import java.util.regex.Pattern;

public class ExecutiveValidator {
    public static void validate(ExecutiveSignupRequest request) {
        FieldValidator.validate("First Name", request.getFirstName(), Constants.ALPHABET_REGEX);
        FieldValidator.validate("Last Name", request.getLastName(), Constants.ALPHABET_REGEX);
        FieldValidator.validate("Email", request.getEmailId(), Constants.EMAIL_REGEX);
        FieldValidator.validate("Password", request.getPassword(), Constants.PASSWORD_REGEX);
        FieldValidator.validateMobileNo(request.getMobileNo());

        if(request.getLocation() == null || request.getLocation().length != 2) {
            throw new ApiException("400","Please provide correct location parameters");
        }
    }

    public static void validate(ExecutiveUpdateRequest request) {
        if(request.getExecutiveId() == null || request.getExecutiveId().equals(0)) {
            throw new ApiException("400","User Id Required");
        }
        if(request.getNewLocation() != null && request.getNewLocation().length != 2) {
            throw new ApiException("400","Please provide correct location parameters");
        }
        if(request.getNewMobileNo() != null && (request.getNewMobileNo().length() != 10 || !Pattern.matches(Constants.DECIMAL_REGEX, request.getNewMobileNo()))) {
            throw new ApiException("400","Mobile No is invalid");
        }
        if(request.getNewPassword() != null && !Pattern.matches(Constants.PASSWORD_REGEX, request.getNewPassword())) {
            throw new ApiException("400", "Password is not strong");
        }
    }

    public static void validate(PasswordUpdateRequest request) {
        if(request.getOldPassword() == null || request.getNewPassword() == null) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        }
        if(!Pattern.matches(Constants.PASSWORD_REGEX, request.getNewPassword())) {
            throw new ApiException("400", "New Password is not strong enough");
        }
    }
}
