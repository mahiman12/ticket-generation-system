package com.example.ticketgenerationsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ResponseBean<T> extends BaseResponse {

    private static final long serialVersionUID = 1L;

    private transient T data;

    public ResponseBean(T data) {
        super();
        this.data = data;
    }

    public ResponseBean() {
        super();
    }

    public ResponseBean(boolean success, String errorCode, String message, T data) {
        super(message, errorCode, success);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
