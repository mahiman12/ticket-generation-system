package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.VehicleConvertor;
import com.example.ticketgenerationsystem.entity.Operator;
import com.example.ticketgenerationsystem.entity.Vehicle;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.VehicleRepo;
import com.example.ticketgenerationsystem.request.VehicleAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepo vehicleRepo;

    public void add(VehicleAddRequest vehicleAddDTO, Operator operator)  {
        try {
            vehicleRepo.save(VehicleConvertor.convert(vehicleAddDTO, operator));
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public List<Vehicle> findAllByOperator(Operator operator)  {
        try {
            return vehicleRepo.findAllByOperator(operator);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public void delete(Vehicle vehicle)  {
        try {
            vehicleRepo.delete(vehicle);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public Vehicle findByRegNo(String regNo)  {
        try {
            return vehicleRepo.findByRegNo(regNo.toLowerCase());
        } catch(Exception e) {
            throw new ApiException("400", e.getMessage());
        }
    }
}
