package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.entity.Comment;
import com.example.ticketgenerationsystem.entity.Ticket;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.request.CommentAddRequest;
import com.example.ticketgenerationsystem.response.CommentObject;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class CommentConvertor {
    public static Comment convert(CommentAddRequest request, Ticket ticket, User user) {
        Comment comment = new Comment();

        comment.setText(request.getText());
        comment.setTicket(ticket);
        comment.setUser(user);
        return comment;
    }

    public static PageableListResponse<CommentObject> convert(Page<Comment> comments) {
        PageableListResponse<CommentObject> commentResponse = new PageableListResponse<>();
        List<CommentObject> commentObjectList = new ArrayList<>();
        commentResponse.setTotalElements(comments.getTotalElements());
        commentResponse.setTotalPages(comments.getTotalPages());
        for(Comment comment: comments.getContent()) {
            CommentObject commentObject = new CommentObject();
            commentObject.setText(comment.getText());
            commentObject.setUserId(comment.getUser().getId());
            commentObjectList.add(commentObject);
        }
        commentResponse.setContent(commentObjectList);
        return commentResponse;
    }
}
