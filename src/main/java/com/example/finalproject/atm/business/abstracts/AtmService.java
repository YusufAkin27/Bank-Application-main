package com.example.finalproject.atm.business.abstracts;

import com.example.finalproject.atm.core.NotFoundCardNumberException;
import com.example.finalproject.atm.core.WrongPasswordException;
import com.example.finalproject.atm.entity.Atm;
import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.response.ServiceResponse;
import com.example.finalproject.transfer.exception.InsufficientBalanceException;

public interface AtmService {
    ServiceResponse process(Atm atm) throws NotFoundDebitCardException, WrongPasswordException, InsufficientBalanceException, DebitCardNotActiveException, NotFoundCardNumberException;
}
