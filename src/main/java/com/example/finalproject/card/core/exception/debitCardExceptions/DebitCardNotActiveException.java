package com.example.finalproject.card.core.exception.debitCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class DebitCardNotActiveException extends BusinessException {

    public DebitCardNotActiveException() {
        super("Banka kartı henüz aktif değil.");
    }
}
