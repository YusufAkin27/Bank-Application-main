package com.example.finalproject.transfer.exception;

import com.example.finalproject.customer.core.exception.BusinessException;

public class WrongNameAndSurnameException extends BusinessException {

    public WrongNameAndSurnameException() {
        super("Yanlış ad ve soyad girildi.");
    }
}
