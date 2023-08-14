package com.example.finalproject.transfer.exception;

import com.example.finalproject.customer.core.exception.BusinessException;

public class IbanDoesNotBelongToYouException extends BusinessException {

    public IbanDoesNotBelongToYouException() {
        super("bu iban size ait değil");
    }
}
