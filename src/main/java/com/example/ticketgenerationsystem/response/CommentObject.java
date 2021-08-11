package com.example.ticketgenerationsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentObject {
    private String text;
    private int userId;
}
