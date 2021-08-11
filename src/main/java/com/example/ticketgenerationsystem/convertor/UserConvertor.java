package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.request.ExecutiveSignupRequest;
import com.example.ticketgenerationsystem.request.OperatorSignupRequest;
import com.example.ticketgenerationsystem.util.HashGenerator;

import java.util.Locale;

public class UserConvertor {
    public static User convert(OperatorSignupRequest request)  {
        User user = new User();

        user.setUserType(Constants.roleMap.get(Constants.ROLE_OPERATOR));
        user.setEmailId(request.getEmailId().toLowerCase());

        user.setPassword(HashGenerator.getMd5(request.getPassword()));
        return user;
    }

    public static User convert(ExecutiveSignupRequest request, int role) {
        User user = new User();

        user.setUserType(role);
        user.setEmailId(request.getEmailId().toLowerCase());

        user.setPassword(HashGenerator.getMd5(request.getPassword()));
        return user;
    }
}
