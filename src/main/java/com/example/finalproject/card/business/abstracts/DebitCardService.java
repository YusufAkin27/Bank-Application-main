package com.example.finalproject.card.business.abstracts;

import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotFoundCheckingAccountException;

import com.example.finalproject.card.core.exception.debitCardExceptions.*;
import com.example.finalproject.card.core.request.CreateDebitCardRequest;
import com.example.finalproject.card.core.response.DebitCardDTO;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.response.ServiceResponse;

import java.util.List;


public interface DebitCardService {

    ServiceResponse add(long customerId,CreateDebitCardRequest createDebitCardRequest) throws NotFoundCheckingAccountException, AccountAlreadyHasADebitCardException, CustomerNotFoundException, NotAccountOwnerException;

    List<DebitCardDTO> getAll();

    ServiceResponse delete(long customerId,long debitCardId ) throws NotFoundDebitCardException, ClientIsAlreadyActiveDebitCard, CustomerNotFoundException, NotCardOwnerException;

    List<Activity> getAllActivity(long customerId,long debitCardId) throws NotFoundDebitCardException, CustomerNotFoundException, NotCardOwnerException;
}
