package com.example.finalproject.card.core.exception.debitCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class CreateNotDebitCardException extends BusinessException {

    public CreateNotDebitCardException() {
        super("Banka kartı oluşturulurken hata oluştu.");
    }
}
