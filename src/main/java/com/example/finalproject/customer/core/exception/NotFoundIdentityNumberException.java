package com.example.finalproject.customer.core.exception;

public class NotFoundIdentityNumberException extends BusinessException {

    public NotFoundIdentityNumberException() {
        super("kimlik numaralı kullanıcı bulunamadı");
    }
}
