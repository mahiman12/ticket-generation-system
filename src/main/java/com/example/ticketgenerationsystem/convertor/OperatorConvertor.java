package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.dto.OperatorDTO;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.request.OperatorSignupRequest;
import com.example.ticketgenerationsystem.entity.Operator;

public class OperatorConvertor {

    public static Operator convert(OperatorSignupRequest request, User user) {
        Operator operator = new Operator();

        operator.setFirstName(request.getFirstName());
        operator.setMobileNo(request.getMobileNo());

        if(request.getLastName() != null) {
            operator.setLastName(request.getLastName());
        }

        operator.setUser(user);

        return operator;
    }

    public static OperatorDTO convert(Operator operator) {
        OperatorDTO dto = new OperatorDTO();
        dto.setFirstName(operator.getFirstName());
        dto.setLastName(operator.getLastName());
        dto.setMobileNo(operator.getMobileNo());
        dto.setEmailId(operator.getUser().getEmailId());
        return dto;
    }
}
