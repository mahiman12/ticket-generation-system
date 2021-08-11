package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.entity.Executive;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.request.ExecutiveSignupRequest;
import com.example.ticketgenerationsystem.response.ExecutiveObject;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ExecutiveConvertor {
    public static Executive convert(ExecutiveSignupRequest request, User user) {
        Executive executive = new Executive();

        executive.setFirstName(request.getFirstName());
        executive.setMobileNo(request.getMobileNo());

        if(request.getLastName() != null) {
            executive.setLastName(request.getLastName());
        }
        executive.setLocation(request.getLocation());

        executive.setUser(user);

        return executive;
    }

    public static PageableListResponse<ExecutiveObject> convert(Page<Executive> executivePage) {
        PageableListResponse<ExecutiveObject> executiveResponse = new PageableListResponse<>();
        List<ExecutiveObject> executiveObjectList = new ArrayList<>();
        executiveResponse.setTotalElements(executivePage.getTotalElements());
        executiveResponse.setTotalPages(executivePage.getTotalPages());
        for(Executive executive : executivePage.getContent()) {
            ExecutiveObject executiveObject = new ExecutiveObject();
            executiveObject.setFirstName(executive.getFirstName());
            executiveObject.setLastName(executive.getLastName());
            executiveObject.setMobileNo(executive.getMobileNo());
            executiveObject.setLocation(executive.getLocation());
            executiveObject.setEmailId(executive.getUser().getEmailId());
            executiveObjectList.add(executiveObject);
        }
        executiveResponse.setContent(executiveObjectList);
        return executiveResponse;
    }
}
