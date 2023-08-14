package com.example.finalproject.account.core.exception.checkingAccountExcepiton;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotCustomerOwnerException extends BusinessException {

    public NotCustomerOwnerException() {
        super("check hesabı size ait değil");
    }
}
