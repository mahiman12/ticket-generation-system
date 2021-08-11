package com.example.ticketgenerationsystem.controller;

import com.example.ticketgenerationsystem.convertor.PageableConvertor;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.request.CommentAddRequest;
import com.example.ticketgenerationsystem.request.CommentUpdateRequest;
import com.example.ticketgenerationsystem.response.CommentObject;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import com.example.ticketgenerationsystem.response.ResponseBean;
import com.example.ticketgenerationsystem.service.AuthorizationService;
import com.example.ticketgenerationsystem.service.CommentService;
import com.example.ticketgenerationsystem.validator.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment/{id}")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private AuthorizationService authService;

    @PostMapping
    public ResponseEntity<ResponseBean<Object>> add(@RequestBody CommentAddRequest request, @PathVariable(name = "id") int ticketId, @RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        CommentValidator.validate(request, ticketId);
        commentService.add(request, ticketId, user);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableListResponse<CommentObject>> get(@PathVariable(name = "id") int ticketId, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(commentService.findByTicketId(ticketId, PageableConvertor.getPageableObject(pageNo, pageSize)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseBean<Object>> update(@RequestBody CommentUpdateRequest request, @PathVariable(name = "id") int commentId, @RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        CommentValidator.validate(request);
        commentService.updateComment(request, commentId, user);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseBean<Object>> delete(@PathVariable(name = "id") int commentId, @RequestHeader("Authorization") String token) {
        User user = authService.authorize(token);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        commentService.delete(commentId, user);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }
}
