package com.example.finalproject.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataServiceResponse<T> extends ServiceResponse {
    private T data;

    public DataServiceResponse(String message, boolean isSuccessful, T data) {
        super(message, isSuccessful);
        this.data = data;
    }
}