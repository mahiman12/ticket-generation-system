package com.example.ticketgenerationsystem.response;

import com.example.ticketgenerationsystem.constant.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponse {

    private String message;

    private String errorCode;

    private boolean success;

    public BaseResponse() {
        this.success = true;
        this.message = Constants.OK;
    }

    public BaseResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public BaseResponse(String message, String errorCode, boolean success) {
        this.message = message;
        this.errorCode = errorCode;
        this.success = success;
    }
}
