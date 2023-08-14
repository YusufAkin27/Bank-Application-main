package com.example.finalproject.account.core.exception.checkingAccountExcepiton;


import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundAccountException extends BusinessException {

    public NotFoundAccountException() {
        super("hesap bulunamadı");
    }
}
