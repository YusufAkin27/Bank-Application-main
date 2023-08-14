package com.example.finalproject.account.core.exception.checkingAccountExcepiton;

import com.example.finalproject.customer.core.exception.BusinessException;

public class AddressCannotBeEmptyException extends BusinessException {

    public AddressCannotBeEmptyException() {
        super("Müşteri adresi doğrulaması yapılmadan hesap açılamaz.");
    }
}
