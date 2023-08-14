package com.example.finalproject.transfer.business.abstracts;

import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.response.ServiceResponse;
import com.example.finalproject.transfer.exception.*;
import com.example.finalproject.transfer.entity.Transfer;

import java.util.List;


public interface TransferService {
    ServiceResponse sendMoney(long customerId,Transfer transfer) throws NotFoundDebitCardException, WrongNameAndSurnameException, DebitCardNotActiveException, InsufficientBalanceException, TransferAmountOutOfRangeException, TransferTimeNotAllowedException, CustomerNotFoundException, IbanDoesNotBelongToYouException, NotFoundIbanException;

    List<Transfer> getAllTransfers() ;


}
