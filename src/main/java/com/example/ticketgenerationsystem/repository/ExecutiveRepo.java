package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.Executive;
import com.example.ticketgenerationsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutiveRepo extends PagingAndSortingRepository<Executive, Integer> {
    Executive findByUser(User user);
    @Query(
            value = "SELECT * FROM EXECUTIVES e INNER JOIN USERS u ON e.user_id = u.id where u.user_type = ?1 and e.deleted = false ORDER BY u.created_at",
            countQuery = "SELECT count(*) FROM EXECUTIVES e INNER JOIN USERS u ON e.user_id = u.id where u.user_type = ?1 and e.deleted = false",
            nativeQuery = true)
    Page<Executive> findAllByRole(int role, Pageable pageable);
}
