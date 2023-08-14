package com.example.finalproject.customer.core.exception;

public class CheckingEmailException extends BusinessException {

    public CheckingEmailException() {
        super("email hatalı girildi ");
    }
}
