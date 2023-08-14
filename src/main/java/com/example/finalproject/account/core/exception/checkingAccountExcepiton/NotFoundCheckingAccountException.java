package com.example.finalproject.account.core.exception.checkingAccountExcepiton;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundCheckingAccountException extends BusinessException {

    public NotFoundCheckingAccountException() {
        super("Checking hesabı bulunamadı.");
    }
}
