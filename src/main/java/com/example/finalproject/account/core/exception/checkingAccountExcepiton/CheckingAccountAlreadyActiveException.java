package com.example.finalproject.account.core.exception.checkingAccountExcepiton;

import com.example.finalproject.customer.core.exception.BusinessException;

public class CheckingAccountAlreadyActiveException extends BusinessException {

    public CheckingAccountAlreadyActiveException() {
        super("Kontrol hesabı zaten aktif.");
    }
}
