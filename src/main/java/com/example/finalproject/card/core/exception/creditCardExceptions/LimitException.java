package com.example.finalproject.card.core.exception.creditCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class LimitException extends BusinessException {

    public LimitException() {
        super("Kredi kartı limitiniz gelirinizle orantılı olarak aşılmıştır.");
    }
}
