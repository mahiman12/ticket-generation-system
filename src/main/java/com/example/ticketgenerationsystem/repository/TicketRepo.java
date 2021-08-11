package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.Ticket;
import com.example.ticketgenerationsystem.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends PagingAndSortingRepository<Ticket, Integer> {
    Page<Ticket> findAllByStatus(int status, Pageable pageable);
    void deleteAllByVehicle(Vehicle vehicle);
}
