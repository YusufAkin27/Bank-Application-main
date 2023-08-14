package com.example.finalproject.account.business.abstracts;


import com.example.finalproject.account.core.exception.checkingAccountExcepiton.AddressCannotBeEmptyException;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.CheckingAccountAlreadyActiveException;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotCustomerOwnerException;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotFoundCheckingAccountException;
import com.example.finalproject.account.core.request.CreateCheckingAccountRequest;
import com.example.finalproject.account.core.response.CheckingAccountDTO;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotAccountOwnerException;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.customer.core.exception.CustomerNotActiveException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.response.ServiceResponse;

import java.util.List;


public interface CheckingAccountService {
    ServiceResponse add(long customerId,CreateCheckingAccountRequest createCheckingAccountRequest) throws
            CustomerNotFoundException, AddressCannotBeEmptyException;

    ServiceResponse delete(long customerId,long checkingAccountId) throws NotFoundCheckingAccountException, CustomerNotFoundException, NotCustomerOwnerException, CustomerNotActiveException;



    ServiceResponse activate(long customerId,long checkingAccountId) throws NotFoundCheckingAccountException, CheckingAccountAlreadyActiveException, NotAccountOwnerException;



    List<CheckingAccountDTO> getAllWithPagination(int page, int pageSize);

    List<CheckingAccountDTO> searchAccount(String info) throws NotFoundCheckingAccountException;

    List<Activity> activateWithPagination(long customerId,long checkingAccountId,int page, int pageSize) throws CustomerNotFoundException, NotAccountOwnerException;
}
