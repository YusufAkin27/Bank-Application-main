package com.example.finalproject.card.core.exception.debitCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotCardOwnerException extends BusinessException {

    public NotCardOwnerException() {
        super("Kartın kullanıcısı olmalısınız");
    }
}
