package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.request.OperatorSignupRequest;
import com.example.ticketgenerationsystem.request.OperatorUpdateRequest;
import com.example.ticketgenerationsystem.request.VehicleAddRequest;
import com.example.ticketgenerationsystem.exception.ApiException;
import java.util.regex.*;

public class OperatorValidator {
    public static void validate(OperatorSignupRequest request) {
        FieldValidator.validate("First Name", request.getFirstName(), Constants.ALPHABET_REGEX);
        FieldValidator.validate("Last Name", request.getLastName(), Constants.ALPHABET_REGEX);
        FieldValidator.validate("Email", request.getEmailId(), Constants.EMAIL_REGEX);
        FieldValidator.validate("Password", request.getPassword(), Constants.PASSWORD_REGEX);
        FieldValidator.validateMobileNo(request.getMobileNo());

        // Checking Validation for vehicle objects
        if(request.getVehicles() == null || request.getVehicles().size() < 1) {
            throw new ApiException("400","No Vehicle provided");
        }
        for(VehicleAddRequest vehicleAddDTO: request.getVehicles()) {
            VehicleValidator.validate(vehicleAddDTO);
        }
    }

    public static void validate(OperatorUpdateRequest request) {
        if(request.getMobileNo() != null && (request.getMobileNo().length() != 10 || !Pattern.matches(Constants.DECIMAL_REGEX, request.getMobileNo()))) {
            throw new ApiException("400","Mobile No is invalid");
        }
    }
}
