package com.example.finalproject.customer.core.exception;

public class ClientIsAlreadyActive extends BusinessException {

    public ClientIsAlreadyActive() {
        super("kullanıcı zaten aktif durumdadır");
    }
}
