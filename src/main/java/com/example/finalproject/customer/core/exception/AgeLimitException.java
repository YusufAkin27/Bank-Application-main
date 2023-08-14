package com.example.finalproject.customer.core.exception;

public class AgeLimitException extends BusinessException {

    public AgeLimitException() {
        super("kayıt olmak için yaşınız min 18 olmalıdır");
    }
}
