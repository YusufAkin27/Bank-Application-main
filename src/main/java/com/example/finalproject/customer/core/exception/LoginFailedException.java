package com.example.finalproject.customer.core.exception;

public class LoginFailedException extends BusinessException {

    public LoginFailedException() {
        super("Giriş yaparken bir hata oluştu.");
    }
}
