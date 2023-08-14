package com.example.finalproject.customer.core.exception;

public class CustomerByIdentityNumberNotUniqueException extends BusinessException {

    public CustomerByIdentityNumberNotUniqueException() {
        super("Aynı kimlik numarasına sahip başka bir kayıt zaten var.");
    }
}
