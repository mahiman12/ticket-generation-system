package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.request.VehicleAddRequest;
import com.example.ticketgenerationsystem.entity.Operator;
import com.example.ticketgenerationsystem.entity.Vehicle;

public class VehicleConvertor {
    public static Vehicle convert(VehicleAddRequest vehicleAddDTO, Operator operatorReference) {
        Vehicle vehicle = new Vehicle();

        vehicle.setRegNo(vehicleAddDTO.getRegNo().toLowerCase());
        vehicle.setModel(vehicleAddDTO.getModel().toLowerCase());
        vehicle.setOperator(operatorReference);

        return vehicle;
    }
}
