package com.example.finalproject.customer.core.exception;

public class AdminRequiredException extends BusinessException {

    public AdminRequiredException() {
        super("buraya sadece adminler erişebilir");
    }
}
