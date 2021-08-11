package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.Operator;
import com.example.ticketgenerationsystem.entity.Vehicle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends PagingAndSortingRepository<Vehicle, Integer> {
    Vehicle findByRegNo(String regNo);
    List<Vehicle> findAllByOperator(Operator operator);
}
