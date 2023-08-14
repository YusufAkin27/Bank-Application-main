package com.example.finalproject.customer.core.exception;

public class AddressNotFoundException extends BusinessException {

    public AddressNotFoundException() {
        super("adres bulunamadı");
    }
}
