package com.example.finalproject.atm.core;

import com.example.finalproject.customer.core.exception.BusinessException;

public class WrongPasswordException extends BusinessException {

    public WrongPasswordException() {
        super("şifre hatalı");
    }
}
