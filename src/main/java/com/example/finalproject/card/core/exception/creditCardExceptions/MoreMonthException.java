package com.example.finalproject.card.core.exception.creditCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class MoreMonthException extends BusinessException {

    public MoreMonthException() {
        super("Taksitler 12 ayı geçemez.");
    }
}
