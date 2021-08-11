package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.request.CommentAddRequest;
import com.example.ticketgenerationsystem.request.CommentUpdateRequest;

import java.util.regex.Pattern;

public class CommentValidator {
    public static void validate(CommentAddRequest request, int ticketId) {
        if(request.getText() == null || !Pattern.matches(Constants.TEXT_REGEX, request.getText())){
            throw new ApiException("400","Comment text Required");
        }
        if(ticketId == 0) {
            throw new ApiException("400","Ticket id Required");
        }
    }

    public static void validate(CommentUpdateRequest request) {
        if(request.getText() == null || !Pattern.matches(Constants.TEXT_REGEX, request.getText())){
            throw new ApiException("400","Comment text Required");
        }
    }
}