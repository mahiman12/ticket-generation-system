package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.request.TicketGenerateRequest;

import java.util.regex.Pattern;

public class TicketValidator {
    public static void validate(TicketGenerateRequest request) {
        if(request.getIssueType() == null || request.getIssueType().equals(0)) {
            throw new ApiException("400","Issue Type Required");
        } else if(!Constants.issueTypeMap.containsValue(request.getIssueType())) {
            throw new ApiException("400",Constants.INVALID_REQUEST_PARAMETERS);
        }

        if(request.getRegNo() == null) {
            throw new ApiException("400","Registration No Required");
        } else if(request.getRegNo().length() > 10 || !Pattern.matches(Constants.ALPHANUMERIC_REGEX, request.getRegNo())) {
            throw new ApiException("400","Registration No is invalid");
        }

        if(request.getLocation() == null || request.getLocation().length != 2) {
            throw new ApiException("400","Please provide correct location parameters");
        }
    }
}
