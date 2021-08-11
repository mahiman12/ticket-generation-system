package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.CommentConvertor;
import com.example.ticketgenerationsystem.entity.Comment;
import com.example.ticketgenerationsystem.entity.Ticket;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.CommentRepo;
import com.example.ticketgenerationsystem.request.CommentAddRequest;
import com.example.ticketgenerationsystem.response.CommentObject;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @MockBean
    private TicketService ticketService;
    @MockBean
    private UserService userService;
    @MockBean
    private CommentRepo commentRepo;

    private User user;
    private Ticket ticket;
    private Comment comment;

    @Before
    public void init() {
        user = new User();
        user.setId(1);
        user.setEmailId("a@b.com");
        user.setPassword("testUser@123");
        user.setUserType(Constants.roleMap.get(Constants.ROLE_EXECUTIVE));

        ticket = new Ticket();
        ticket.setId(1);
        ticket.setStatus(Constants.ticketStatusMap.get(Constants.TICKET_STATUS_ACTIVE));

        comment = new Comment();
        comment.setText("Test Comment");
        comment.setTicket(ticket);
        comment.setUser(user);
        comment.setId(1);
    }

    @Test
    public void addCommentTest() {
        CommentAddRequest request = new CommentAddRequest();
        request.setText("Test Comment");

        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(ticketService.findById(1)).thenReturn(ticket);
        when(commentRepo.save(comment)).thenReturn(comment);

        Comment comment1 = commentService.add(request, 1, user);
        assertEquals(comment.getId(), comment1.getId());
    }

    @Test
    public void findByTicketIdTest() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Comment> list= new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setText("Test Comment 1");
        comment1.setTicket(ticket);
        comment1.setUser(user);
        comment1.setId(2);
        list.add(comment);
        list.add(comment1);
        Page<Comment> page = new PageImpl<>(list, pageable, list.size());

        PageableListResponse<CommentObject> commentList = CommentConvertor.convert(page);

        when(ticketService.findById(1)).thenReturn(ticket);
        when(commentRepo.findAllByTicket(ticket.getId(), pageable)).thenReturn(page);

        PageableListResponse<CommentObject> commentResponse = commentService.findByTicketId(1, pageable);

        assertEquals(commentList.getContent().size(), commentResponse.getContent().size());

        Comment comment2 = new Comment();
        list.add(comment2);
        page = new PageImpl<>(list, pageable, list.size());
        commentList = CommentConvertor.convert(page);
        commentResponse = commentService.findByTicketId(1, pageable);
        assertEquals(commentList.getContent().size(), commentResponse.getContent().size());
    }

    @Test(expected = ApiException.class)
    public void findByTicketIdExceptionTest() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Comment> list= new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setText("Test Comment 1");
        comment1.setTicket(ticket);
        comment1.setUser(user);
        comment1.setId(2);
        list.add(comment);
        list.add(comment1);
        Comment comment2 = new Comment();
        list.add(comment2);
        Page<Comment> page = new PageImpl<>(list, pageable, list.size());

        when(ticketService.findById(1)).thenReturn(ticket);
        when(commentRepo.findAllByTicket(ticket.getId(), pageable)).thenReturn(page);

        PageableListResponse<CommentObject> commentResponse = commentService.findByTicketId(1, pageable);
    }
}
