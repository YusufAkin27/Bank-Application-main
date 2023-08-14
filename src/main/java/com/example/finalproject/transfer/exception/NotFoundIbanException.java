package com.example.finalproject.transfer.exception;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundIbanException extends BusinessException {

    public NotFoundIbanException() {
        super("böyle bi iban bulunmamakta");
    }
}
