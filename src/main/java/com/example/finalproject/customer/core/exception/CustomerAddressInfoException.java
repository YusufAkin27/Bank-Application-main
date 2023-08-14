package com.example.finalproject.customer.core.exception;

public class CustomerAddressInfoException extends BusinessException {

    public CustomerAddressInfoException() {
        super("Müşteri zaten adres bilgisine sahiptir.");
    }
}
