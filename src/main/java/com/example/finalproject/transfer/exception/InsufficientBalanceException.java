package com.example.finalproject.transfer.exception;

import com.example.finalproject.customer.core.exception.BusinessException;

public class InsufficientBalanceException extends BusinessException {

    public InsufficientBalanceException() {
        super("Hesapta yetersiz bakiye.");
    }
}
