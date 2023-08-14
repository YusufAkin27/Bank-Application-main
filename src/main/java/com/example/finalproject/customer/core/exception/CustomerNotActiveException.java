package com.example.finalproject.customer.core.exception;

public class CustomerNotActiveException extends BusinessException {

    public CustomerNotActiveException() {
        super("Müşterinin hesabı henüz etkinleştirilmedi.");
    }
}
