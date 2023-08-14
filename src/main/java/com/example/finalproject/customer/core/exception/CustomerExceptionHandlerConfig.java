package com.example.finalproject.customer.core.exception;


import com.example.finalproject.response.ServiceResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandlerConfig extends Throwable {

    @ExceptionHandler(value = BusinessException.class)
    public ServiceResponse businessExceptionHandler(BusinessException businessException) {
        return new ServiceResponse(businessException.getMessage(), false);
    }

}