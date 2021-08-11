package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.OperatorConvertor;
import com.example.ticketgenerationsystem.convertor.UserConvertor;
import com.example.ticketgenerationsystem.dto.OperatorDTO;
import com.example.ticketgenerationsystem.entity.Operator;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.entity.Vehicle;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.OperatorRepo;
import com.example.ticketgenerationsystem.request.OperatorSignupRequest;
import com.example.ticketgenerationsystem.request.OperatorUpdateRequest;
import com.example.ticketgenerationsystem.request.VehicleAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepo operatorRepo;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;

    @Transactional
    public Operator add(OperatorSignupRequest request)  {

        try {
            User user = userService.add(UserConvertor.convert(request));
            Operator operator = operatorRepo.save(OperatorConvertor.convert(request, user));

            for (VehicleAddRequest vehicleAddDTO : request.getVehicles()) {
                vehicleService.add(vehicleAddDTO, operator);
            }
            return operator;
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public Operator update(OperatorUpdateRequest operatorUpdateDTO, User user)  {
        Optional<Operator> optionalOperator = operatorRepo.findByUser(user);
        if (!optionalOperator.isPresent()) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        }

        Operator operator = optionalOperator.get();
        operator.setMobileNo(operatorUpdateDTO.getMobileNo());

        try {
            return operatorRepo.save(operator);
        } catch (Exception e) {
            throw new ApiException("400", e.getMessage());
        }
    }

    @Transactional
    public void delete(User user)  {
        Optional<Operator> optionalOperator = operatorRepo.findByUser(user);
        if (!optionalOperator.isPresent()) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        }

        Operator operator = optionalOperator.get();

        try {
            List<Vehicle> vehicles = vehicleService.findAllByOperator(operator);
            for (Vehicle vehicle : vehicles) {
                vehicleService.delete(vehicle);
                ticketService.deleteAllByVehicle(vehicle);
            }

            userService.delete(operator.getUser().getId());
            operatorRepo.delete(operator);
        } catch (Exception e) {
            throw new ApiException("400", e.getMessage());
        }
    }

    public List<OperatorDTO> findAll()  {
        Iterable <Operator> operators = operatorRepo.findAll();
        List<OperatorDTO> operatorDTOList = new ArrayList<>();
        for(Operator operator: operators) {
            operatorDTOList.add(OperatorConvertor.convert(operator));
        }
        return operatorDTOList;
    }

    public OperatorDTO findById(int operatorId)  {
        Optional<Operator> operatorOptional = operatorRepo.findById(operatorId);
        if(operatorOptional.isPresent()) {
            return OperatorConvertor.convert(operatorOptional.get());
        }
        throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
    }
}
