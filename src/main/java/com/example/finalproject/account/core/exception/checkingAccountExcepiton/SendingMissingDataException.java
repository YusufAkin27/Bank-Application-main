package com.example.finalproject.account.core.exception.checkingAccountExcepiton;

import com.example.finalproject.customer.core.exception.BusinessException;

public class SendingMissingDataException extends BusinessException {

    public SendingMissingDataException() {
        super("Eksik veya yanlış veri gönderdiniz, lütfen tekrar deneyin.");
    }
}
