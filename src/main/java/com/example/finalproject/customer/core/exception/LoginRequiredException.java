package com.example.finalproject.customer.core.exception;

public class LoginRequiredException extends BusinessException {

    public LoginRequiredException() {
        super("giriş yapma zorunluluğunuz var");
    }
}
