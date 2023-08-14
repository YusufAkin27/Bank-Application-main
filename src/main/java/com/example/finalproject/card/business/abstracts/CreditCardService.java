package com.example.finalproject.card.business.abstracts;


import com.example.finalproject.atm.core.WrongPasswordException;
import com.example.finalproject.card.core.exception.creditCardExceptions.*;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotCardOwnerException;
import com.example.finalproject.card.core.request.DepositMoneyRequest;
import com.example.finalproject.card.core.request.WithdrawalMoneyRequest;
import com.example.finalproject.card.core.response.CreditCardDTO;
import com.example.finalproject.card.entity.CreditCard;
import com.example.finalproject.card.core.request.CreateCreditCardRequest;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.customer.core.exception.CustomerNotActiveException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;

import com.example.finalproject.response.DataServiceResponse;
import com.example.finalproject.response.ServiceResponse;

import java.util.List;


public interface CreditCardService {
    DataServiceResponse<CreditCard> add(long customerId, String password) throws CustomerNotFoundException, CreateCreditCardException;

    ServiceResponse doActive(long creditCardId, long customerId) throws NotFoundCreditCardException, AlreadyActiveCreditCardException, CustomerNotFoundException, ThisCreditCardIsNotYoursException, CustomerNotActiveException;

    CreditCard getIdentityNumber(String identityNu) throws NotFoundCreditCardException;

    ServiceResponse delete(long creditCardId,long customerId) throws NotFoundCreditCardException, CreditCardStillHasDebtException, CustomerNotFoundException, NotCardOwnerException;

    List<CreditCardDTO> getAll();

    CreditCardDTO withdrawal(long customerId,WithdrawalMoneyRequest withdrawalMoneyRequest) throws NotFoundCreditCardException, WrongPasswordException, LimitException, MoreMonthException, CustomerNotFoundException, NotCardOwnerException;

    CreditCardDTO deposit(long customerId,DepositMoneyRequest depositMoneyRequest) throws NotFoundCreditCardException, WrongPasswordException, CustomerNotFoundException, NotCardOwnerException;

    List<Activity> getAllActivity(long customerId,long creditCardId) throws NotFoundCreditCardException, CustomerNotFoundException, NotCardOwnerException;
}
