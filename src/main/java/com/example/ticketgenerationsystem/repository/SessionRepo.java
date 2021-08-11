package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.Session;
import com.example.ticketgenerationsystem.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepo extends PagingAndSortingRepository<Session, Integer> {
    Optional<Session> findByUser(User user);
    Optional<Session> findByUuid(String uuid);
}
