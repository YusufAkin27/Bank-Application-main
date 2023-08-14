
package com.example.finalproject.transfer.exception;

import com.example.finalproject.customer.core.exception.BusinessException;

public class TransferTimeNotAllowedException extends BusinessException {

    public TransferTimeNotAllowedException() {
        super("Sadece 06:00 ile 23:00 saatleri arasında transfer gerçekleştirebilirsiniz.");
    }
}
