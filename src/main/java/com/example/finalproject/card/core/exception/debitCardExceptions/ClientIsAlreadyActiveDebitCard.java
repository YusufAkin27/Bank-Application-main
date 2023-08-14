package com.example.finalproject.card.core.exception.debitCardExceptions;

import com.example.finalproject.customer.core.exception.BusinessException;

public class ClientIsAlreadyActiveDebitCard extends BusinessException {

    public ClientIsAlreadyActiveDebitCard() {
        super("Banka kartı zaten aktif durumda.");
    }
}
