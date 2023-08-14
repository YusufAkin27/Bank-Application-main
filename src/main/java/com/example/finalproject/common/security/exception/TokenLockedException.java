package com.example.finalproject.common.security.exception;


import com.example.finalproject.customer.core.exception.BusinessException;

public class TokenLockedException extends BusinessException {
    public TokenLockedException() {
        super("token kilitli lütfen tekrar giriniz");
    }
}
