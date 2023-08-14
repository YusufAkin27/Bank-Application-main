package com.example.finalproject.account.core.exception.savingAccountException;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundSavingAccountException extends BusinessException {

    public NotFoundSavingAccountException() {
        super("Tasarruf hesabı bulunamadı.");
    }
}
