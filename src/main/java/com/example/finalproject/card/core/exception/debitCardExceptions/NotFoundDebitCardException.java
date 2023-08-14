package com.example.finalproject.card.core.exception.debitCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundDebitCardException extends BusinessException {

    public NotFoundDebitCardException() {
        super("Banka kartı bulunamadı.");
    }
}
