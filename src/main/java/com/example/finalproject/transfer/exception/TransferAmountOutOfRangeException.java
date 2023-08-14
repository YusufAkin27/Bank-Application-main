package com.example.finalproject.transfer.exception;

import com.example.finalproject.customer.core.exception.BusinessException;

public class TransferAmountOutOfRangeException extends BusinessException {

    public TransferAmountOutOfRangeException() {
        super("Minimum transfer tutarını aşmanız gerekiyor.");
    }
}
