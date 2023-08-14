package com.example.finalproject.atm.core;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundCardNumberException extends BusinessException {

    public NotFoundCardNumberException() {
        super("Sağlanan kart numarasına sahip kart bulunamadı.");
    }
}
