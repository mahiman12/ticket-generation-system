package com.example.ticketgenerationsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class PageableListResponse<T> {
    private Long totalElements;
    private Integer totalPages;
    private List<T> content;
}
