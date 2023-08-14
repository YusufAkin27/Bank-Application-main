package com.example.finalproject.customer.core.exception;

public class CustomerByEmailNotUniqueException extends BusinessException {

    public CustomerByEmailNotUniqueException() {
        super("Aynı e-posta adresine sahip başka bir kayıt zaten var.");
    }
}
