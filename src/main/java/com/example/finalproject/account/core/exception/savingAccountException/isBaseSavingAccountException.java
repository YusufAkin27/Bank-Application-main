package com.example.finalproject.account.core.exception.savingAccountException;

import com.example.finalproject.customer.core.exception.BusinessException;

public class isBaseSavingAccountException extends BusinessException {

    public isBaseSavingAccountException() {
        super("Tasarruf hesabı zaten açılmıştır.");
    }
}
