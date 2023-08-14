package com.example.finalproject.card.core.exception.creditCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class CreateCreditCardException extends BusinessException {

    public CreateCreditCardException() {
        super("kredi kartı oluşturma hatası");
    }
}
