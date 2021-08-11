package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.exception.ApiException;

import java.util.regex.Pattern;

public class FieldValidator {
    public static void validate(String fieldName, String fieldVal, String regexp) {
        if(fieldVal == null || fieldVal.equals("")) {
            throw new ApiException("400",String.format("%s required", fieldName));
        }else if(!Pattern.matches(regexp, fieldVal)) {
            throw new ApiException("400",String.format("%s is invalid", fieldName));
        }
    }

    public static void validateMobileNo(String mobileNo) {
        // Mobile No must be non null and contains digits only with limit 10
        if(mobileNo == null) {
            throw new ApiException("400","Mobile No Required");
        }else if(mobileNo.length() != 10 || !Pattern.matches(Constants.DECIMAL_REGEX, mobileNo)) {
            throw new ApiException("400","Mobile No is invalid");
        }
    }
}
