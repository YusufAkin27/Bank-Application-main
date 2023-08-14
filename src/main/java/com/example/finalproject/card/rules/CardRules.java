package com.example.finalproject.card.rules;

import com.example.finalproject.account.business.concretes.CheckingAccountManager;
import com.example.finalproject.card.core.exception.creditCardExceptions.LimitException;
import com.example.finalproject.card.core.exception.debitCardExceptions.AccountAlreadyHasADebitCardException;
import com.example.finalproject.card.entity.CreditCard;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotFoundCheckingAccountException;
import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.card.entity.DebitCard;
import com.example.finalproject.card.repository.CreditCardRepository;
import com.example.finalproject.card.repository.DebitCardRepository;
import com.example.finalproject.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardRules {

    private final CheckingAccountManager checkingAccountManager;

    private final CreditCardRepository creditCardRepository;

    private final DebitCardRepository debitCardRepository;

    public boolean AlreadyAccountHasADebitCard(long checkingAccountId) throws NotFoundCheckingAccountException, AccountAlreadyHasADebitCardException {
        CheckingAccount checkingAccount = checkingAccountManager.findByIdCheckingAccount(checkingAccountId);
        if (checkingAccount.getDebitCard() != null) {
            throw new AccountAlreadyHasADebitCardException();
        }
        return true;
    }

    public boolean withdrawalMoney(CreditCard creditCard, BigDecimal amount) throws LimitException {
        Customer customer = creditCard.getCustomer();
        BigDecimal currentDebt = creditCard.getDebt();
        BigDecimal customerIncome = customer.getIncome();
        BigDecimal totalDebtLimit;

        if (customerIncome.compareTo(new BigDecimal("8500")) == 0) {
            totalDebtLimit = new BigDecimal("10000");
        } else if (customerIncome.compareTo(new BigDecimal("12000")) >= 0) {
            totalDebtLimit = new BigDecimal("30000");
        } else {
            totalDebtLimit = new BigDecimal("17000");
        }

        BigDecimal newDebt = currentDebt.add(amount);

        if (newDebt.compareTo(totalDebtLimit) > 0) {
            throw new LimitException();
        }

        return true;
    }

    public boolean isCardNumberUnique(String cardNumber)   {
        CreditCard existingCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if (existingCreditCard != null) {
          return false;
        }

        DebitCard existingDebitCard = debitCardRepository.findByCardNumber(cardNumber);
        if (existingDebitCard != null) {
          return false;
        }

        return true;
    }
    public boolean isIbanUnique(String iban)   {
        CreditCard existingCreditCard = creditCardRepository.findByIban(iban);
        if (existingCreditCard != null) {
            return false;
        }

        DebitCard existingDebitCard = debitCardRepository.findByIban(iban);
        if (existingDebitCard != null) {
            return false;
        }

        return true;
    }


}
