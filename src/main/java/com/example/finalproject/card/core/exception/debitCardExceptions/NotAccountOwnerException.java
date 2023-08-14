package com.example.finalproject.card.core.exception.debitCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotAccountOwnerException extends BusinessException {

    public NotAccountOwnerException() {
        super("hesabın kullanıcısı olmalısınız");
    }
}
