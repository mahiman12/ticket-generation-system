package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.request.VehicleAddRequest;
import com.example.ticketgenerationsystem.exception.ApiException;

import java.util.regex.Pattern;

public class VehicleValidator {
    public static void validate(VehicleAddRequest vehicleAddDTO) {
        // Registration No must be non null and contains alphanumeric with upper limit 10
        if(vehicleAddDTO.getRegNo() == null) {
            throw new ApiException("400","Registration No Required");
        }else if(vehicleAddDTO.getRegNo().length() > 10 || !Pattern.matches(Constants.ALPHANUMERIC_REGEX, vehicleAddDTO.getRegNo())) {
            throw new ApiException("400","Registration No is invalid");
        }

        if(vehicleAddDTO.getModel() == null) {
            throw new ApiException("400","Model Required");
        } else if (!Pattern.matches(Constants.ALPHANUMERIC_REGEX, vehicleAddDTO.getRegNo())) {
            throw new ApiException("400","Model is invalid");
        }
    }
}
