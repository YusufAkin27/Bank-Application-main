package com.example.finalproject.transfer.exception;

import com.example.finalproject.customer.core.exception.BusinessException;

public class NotFoundTransferException extends BusinessException {

    public NotFoundTransferException() {
        super("Belirtilen kimliğe sahip aktarım bulunamadı.");
    }
}
