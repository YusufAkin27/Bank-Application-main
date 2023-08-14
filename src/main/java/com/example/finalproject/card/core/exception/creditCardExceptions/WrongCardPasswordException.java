package com.example.finalproject.card.core.exception.creditCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class WrongCardPasswordException extends BusinessException {

    public WrongCardPasswordException() {
        super("kartlar 4 haneli şifrelerden oluşur");
    }
}
