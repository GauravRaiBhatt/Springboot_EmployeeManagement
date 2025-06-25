package com.tgrb.banking.utility;

import lombok.Data;

import java.util.Map;

@Data
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int statusCode;
    private String errorMessage;
    private Map<String,String> errors;

    public APIResponse(boolean success, T data, int statusCode){
        this.success = success;
        this.data = data;
        this.statusCode = statusCode;
    }
}
