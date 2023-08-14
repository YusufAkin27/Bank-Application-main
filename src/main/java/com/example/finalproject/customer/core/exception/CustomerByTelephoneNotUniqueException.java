package com.example.finalproject.customer.core.exception;

public class CustomerByTelephoneNotUniqueException extends BusinessException {

    public CustomerByTelephoneNotUniqueException() {
        super("Aynı telefon numarasına sahip başka bir kayıt zaten var.");
    }
}
