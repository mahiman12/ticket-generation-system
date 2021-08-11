package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.CommentConvertor;
import com.example.ticketgenerationsystem.entity.Comment;
import com.example.ticketgenerationsystem.entity.Ticket;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.CommentRepo;
import com.example.ticketgenerationsystem.request.CommentAddRequest;
import com.example.ticketgenerationsystem.request.CommentUpdateRequest;
import com.example.ticketgenerationsystem.response.CommentObject;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private TicketService ticketService;

    public Comment add(CommentAddRequest request, int ticketId, User user) {
        try {
            Ticket ticket = ticketService.findById(ticketId);
            if(ticket == null) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            Comment comment =  commentRepo.save(CommentConvertor.convert(request, ticket, user));
            log.info(String.format("Comment `%s` added", request.getText()));
            return comment;
        } catch(DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public PageableListResponse<CommentObject> findByTicketId(int ticketId, Pageable pageable) {
        try {
            Ticket ticket = ticketService.findById(ticketId);
            if (ticket == null) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            return CommentConvertor.convert(commentRepo.findAllByTicket(ticket.getId(), pageable));
        } catch(DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public void updateComment(CommentUpdateRequest request, int commentId, User user) {
        try {
            Optional<Comment> commentOptional = commentRepo.findById(commentId);
            if(!commentOptional.isPresent()) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            Comment comment = commentOptional.get();
            if(comment.getUser() != user) {
                throw new ApiException("401", "Unauthorized");
            }
            comment.setText(request.getText());
            commentRepo.save(comment);
        } catch(DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public void delete(int commentId, User user) {
        try {
            Optional<Comment> commentOptional = commentRepo.findById(commentId);
            if(!commentOptional.isPresent() || commentOptional.get().getUser() != user) {
                return;
            }
            commentRepo.delete(commentOptional.get());
        } catch(DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }
}