package com.example.finalproject.account.business.abstracts;


import com.example.finalproject.account.core.exception.savingAccountException.NotFoundSavingAccountException;
import com.example.finalproject.account.core.exception.savingAccountException.isBaseSavingAccountException;
import com.example.finalproject.account.core.request.AddMoneySavingAccount;
import com.example.finalproject.account.core.request.CreateSavingAccountRequest;

import com.example.finalproject.account.core.response.SavingAccountDTO;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotAccountOwnerException;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.response.DataServiceResponse;
import com.example.finalproject.response.ServiceResponse;

import java.util.List;


public interface SavingAccountService {
    ServiceResponse add(long customerId,CreateSavingAccountRequest createSavingAccountRequest) throws CustomerNotFoundException, isBaseSavingAccountException, NotAccountOwnerException;

    ServiceResponse delete(long customerId,long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException;

    List<Activity> getActivities(long customerId,long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException;

    List<SavingAccountDTO> getAll();

    ServiceResponse addMoney(long customerId,AddMoneySavingAccount addMoneySavingAccount) throws NotFoundSavingAccountException, NotAccountOwnerException;

    ServiceResponse doActive(long customerId,long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException;


}
