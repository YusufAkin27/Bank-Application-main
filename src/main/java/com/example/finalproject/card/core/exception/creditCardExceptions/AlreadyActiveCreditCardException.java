package com.example.finalproject.card.core.exception.creditCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class AlreadyActiveCreditCardException extends BusinessException {

    public AlreadyActiveCreditCardException() {
        super("kredi kartı zaten aktif durumda");
    }
}
