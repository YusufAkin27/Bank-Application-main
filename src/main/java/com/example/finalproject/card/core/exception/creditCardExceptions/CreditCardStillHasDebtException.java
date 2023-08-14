package com.example.finalproject.card.core.exception.creditCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class CreditCardStillHasDebtException extends BusinessException {

    public CreditCardStillHasDebtException() {
        super("Kredi kartının hala ödenmemiş borcu var.");
    }
}
