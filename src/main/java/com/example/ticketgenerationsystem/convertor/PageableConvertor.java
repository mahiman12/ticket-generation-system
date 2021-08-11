package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.constant.Constants;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class PageableConvertor {
    public static Pageable getPageableObject(Integer pageNo, Integer pageSize) {
        if(pageNo == null || pageSize == null) {
            return PageRequest.of(Constants.DEFAULT_PAGE_NO, Constants.DEFAULT_PAGE_SIZE);
        }
        return PageRequest.of(pageNo, pageSize);
    }
}
