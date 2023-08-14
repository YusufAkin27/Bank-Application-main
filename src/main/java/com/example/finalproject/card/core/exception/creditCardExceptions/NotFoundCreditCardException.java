package com.example.finalproject.card.core.exception.creditCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundCreditCardException extends BusinessException {

    public NotFoundCreditCardException() {
        super("kredi kartı bulunamadı.");
    }
}
