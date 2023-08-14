package com.example.finalproject.common.security.token.core.exception;


import com.example.finalproject.customer.core.exception.BusinessException;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException() {
        super("Token bulunamadı");
    }
}
