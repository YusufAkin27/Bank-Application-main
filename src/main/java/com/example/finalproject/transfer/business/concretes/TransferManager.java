package com.example.finalproject.transfer.business.concretes;

import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.card.entity.CreditCard;
import com.example.finalproject.card.entity.DebitCard;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.card.entity.base.Card;
import com.example.finalproject.card.repository.CardActivityRepository;
import com.example.finalproject.card.repository.CreditCardRepository;
import com.example.finalproject.card.repository.DebitCardRepository;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.customer.entity.Customer;
import com.example.finalproject.customer.repository.CustomerRepository;
import com.example.finalproject.transfer.business.abstracts.TransferService;
import com.example.finalproject.response.ServiceResponse;
import com.example.finalproject.transfer.exception.*;
import com.example.finalproject.transfer.entity.Transfer;
import com.example.finalproject.transfer.repository.TransferRepository;
import com.example.finalproject.transfer.rules.TransferRules;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferManager implements TransferService {
    private final TransferRepository transferRepository;
    private final TransferRules transferRules;
    private final CardActivityRepository cardActivityRepository;
    private final CreditCardRepository creditCardRepository;
    private final DebitCardRepository debitCardRepository;
    private final CustomerRepository customerRepository;


    @Override
    @Transactional
    public ServiceResponse sendMoney(long customerId, Transfer transfer) throws
            InsufficientBalanceException, DebitCardNotActiveException, WrongNameAndSurnameException, TransferAmountOutOfRangeException, TransferTimeNotAllowedException, CustomerNotFoundException, IbanDoesNotBelongToYouException, NotFoundIbanException {


        //müşteri ibanı kendisine ait mi ?
        Customer customer = customerRepository.findByCustomerId(customerId);
        List<CheckingAccount> checkingAccounts = customer.getCheckingAccounts();
        List<DebitCard> debitCards = new ArrayList<>();
        for (CheckingAccount checkingAccount : checkingAccounts) {
            debitCards.add(checkingAccount.getDebitCard());
        }
        List<CreditCard> creditCards = customer.getCreditCards();
        DebitCard debitCard = debitCards.stream().filter(debitCard1 -> debitCard1.getIban().equals(transfer.getMyIban())).findFirst().orElse(null);
        CreditCard creditCard = creditCards.stream().filter(creditCard1 -> creditCard1.getIban().equals(transfer.getMyIban())).findFirst().orElse(null);


        // alıcının ibanıyla banka yada kredi kartını bulma
        DebitCard debitCard1 = debitCardRepository.findByIban(transfer.getIban());
        CreditCard creditCard1 = creditCardRepository.findByIban(transfer.getIban());

        if (debitCard1 == null && creditCard1 == null) {
            throw new NotFoundIbanException();
        }

        if (debitCard == null && debitCard1 == null) {
            return process(creditCard, creditCard1, transfer, null, null);
        } else if (creditCard == null && creditCard1 == null) {
            return process(debitCard, debitCard1, transfer, debitCard.getCheckingAccount(), debitCard1.getCheckingAccount());
        } else if (creditCard == null && debitCard1 == null) {
            return process(debitCard, creditCard1, transfer, debitCard.getCheckingAccount(), null);
        } else if (debitCards == null && creditCard1 == null) {
            return process(creditCard, debitCard1, transfer, null, debitCard1.getCheckingAccount());
        }
        //burada iban kullanıcıya ait değilse
        else {
            throw new IbanDoesNotBelongToYouException();
        }

    }

    @Transactional
    public ServiceResponse process(Card card, Card card1, Transfer transfer, CheckingAccount checkingAccount, CheckingAccount checkingAccount1) throws DebitCardNotActiveException, WrongNameAndSurnameException, InsufficientBalanceException, TransferAmountOutOfRangeException, TransferTimeNotAllowedException {

        Instant currentTime = Instant.now();
        LocalTime now = LocalTime.now();

        transferRules.isActive(card);
        transferRules.isActive(card1);
        transferRules.expiryDate(card);
        transferRules.expiryDate(card1);
        transferRules.nameAndSurnameControl(card1, transfer.getNameAndSurname());
        transferRules.balanceControl(card.getBalance(), transfer.getAmount());
        transferRules.checkTransferAmount(transfer.getAmount());
        transferRules.checkTransferTime(now);


        card1.setBalance(card1.getBalance().add(transfer.getAmount()));
        card.setBalance(card.getBalance().subtract(transfer.getAmount()));

        if (checkingAccount != null) {
            checkingAccount.setBalance(card.getBalance());
        }
        if (checkingAccount1 != null) {
            checkingAccount1.setBalance(card1.getBalance());
        }
        transferRepository.save(transfer);
        addActivity(transfer.getDescription(), currentTime, transfer.getMyIban(), transfer.getAmount(), card1, checkingAccount1);
        addActivity(transfer.getDescription(), currentTime, transfer.getIban(), transfer.getAmount(), card, checkingAccount);

        return new ServiceResponse("Transfer başarılı: " + transfer.getAmount() + " aktarılan miktar", true);
    }

    @Transactional
    public void addActivity(String description, Instant currentTime, String crossAccount, BigDecimal amount, Card card, CheckingAccount checkingAccount) {
        Activity activity = new Activity();
        activity.setAmount(amount);
        activity.setDescription(description);
        activity.setProcessDate(currentTime);
        activity.setCrossAccount(crossAccount);
        if (checkingAccount != null) {
            checkingAccount.addCheckingAccountActivity(activity);
        }
        card.addCardActivity(activity);
        cardActivityRepository.save(activity);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }


}


