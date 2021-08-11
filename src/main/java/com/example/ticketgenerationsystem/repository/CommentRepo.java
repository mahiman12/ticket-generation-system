package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends PagingAndSortingRepository<Comment, Integer> {
    @Query(
            value = "SELECT * FROM COMMENTS where ticket_id = ?1 and deleted = false ORDER BY created_at",
            countQuery = "SELECT count(*) FROM COMMENTS where ticket_id = ?1 and deleted = false",
            nativeQuery = true)
    Page<Comment> findAllByTicket(int ticketId, Pageable pageable);
}
