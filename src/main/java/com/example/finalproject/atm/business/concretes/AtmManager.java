package com.example.finalproject.atm.business.concretes;

import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.atm.business.abstracts.AtmService;
import com.example.finalproject.atm.core.NotFoundCardNumberException;
import com.example.finalproject.atm.core.WrongPasswordException;
import com.example.finalproject.atm.entity.Atm;
import com.example.finalproject.atm.repository.AtmRepository;
import com.example.finalproject.card.business.concretes.CreditCardManager;
import com.example.finalproject.card.business.concretes.DebitCardManager;
import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.entity.CreditCard;
import com.example.finalproject.card.entity.DebitCard;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.card.repository.CardActivityRepository;
import com.example.finalproject.response.ServiceResponse;
import com.example.finalproject.transfer.exception.InsufficientBalanceException;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtmManager implements AtmService {

    private final AtmRepository atmRepository;

    private final DebitCardManager debitCardManager;

    private final CreditCardManager creditCardManager;

    private final CardActivityRepository cardActivityRepository;
    Instant currentTime = Instant.now();

    @Override
    public ServiceResponse process(Atm atm) throws WrongPasswordException, InsufficientBalanceException, DebitCardNotActiveException, NotFoundCardNumberException {

        List<DebitCard> debitCards = debitCardManager.getAllDebitCards();
        List<CreditCard> creditCards = creditCardManager.getAllCreditCards();
        DebitCard debitCard = debitCards.stream().filter(debitCard1 -> debitCard1.getCardNumber().equals(atm.getCardNumber())).findFirst().orElse(null);
        CreditCard creditCard = creditCards.stream().filter(creditCard1 -> creditCard1.getCardNumber().equals(atm.getCardNumber())).findFirst().orElse(null);
        if (creditCard == null && debitCard == null) {
            throw new NotFoundCardNumberException();
        }

        if (debitCard != null) {
            if (atm.getProcess() == atm.getProcess().DEPOSIT) {

                if (!debitCard.isActive()) {
                    throw new DebitCardNotActiveException();
                }
                if (atm.getPassword().equals(debitCard.getPassword())) {
                    debitCard.setBalance(debitCard.getBalance().add(atm.getAmount()));
                    atmRepository.save(atm);
                    String description = "ATM para yatırma işlemi";

                    addActivity(description, currentTime, "", atm.getAmount(), debitCard);
                } else {
                    throw new WrongPasswordException();
                }
                return new ServiceResponse("kalan bakiyeniz: " + debitCard.getBalance(), true);


            }
            if (atm.getProcess() == atm.getProcess().WITHDRAWAL) {
                if (!debitCard.isActive()) {
                    throw new DebitCardNotActiveException();
                }
                if (atm.getPassword().equals(debitCard.getPassword())) {
                    int result = debitCard.getBalance().compareTo(atm.getAmount());
                    if (result < 0) {
                        debitCard.setBalance(debitCard.getBalance().subtract(atm.getAmount()));
                        debitCard.setBalance(debitCard.getBalance().add(atm.getAmount()));
                        atmRepository.save(atm);
                        String description = "ATM den para çekme işlemi";
                        addActivity(description, currentTime, "", atm.getAmount(), debitCard);
                    } else {
                        throw new InsufficientBalanceException();
                    }
                } else {
                    throw new WrongPasswordException();
                }
                return new ServiceResponse("kalan bakiyeniz: " + debitCard.getBalance(), true);


            }
            if (atm.getProcess() == atm.getProcess().BALANCE_INQUIRY) {
                if (!debitCard.isActive()) {
                    throw new DebitCardNotActiveException();
                }
                if (atm.getPassword().equals(debitCard.getPassword())) {
                    String description = "ATM bakiye sorgulama işlemi";

                    addActivity(description, currentTime, "", null, debitCard);
                    return new ServiceResponse("bakiyeniz : " + debitCard.getBalance(), true);
                }
            }
            return new ServiceResponse("İşlem sırasında bir hata oluştu.", false);

        } else {
            return new ServiceResponse("Kredi kartı işlemleri şu anda aktif değildir.", true);

        }
    }

    @Transactional
    public void addActivity(String description, Instant currentTime, String crossAccount, BigDecimal amount, DebitCard debitCard) {
        Activity activity = new Activity();
        activity.setAmount(amount);
        activity.setDescription(description);
        activity.setProcessDate(currentTime);
        activity.setCrossAccount(crossAccount);
        debitCard.addCardActivity(activity);
        CheckingAccount checkingAccount = debitCard.getCheckingAccount();
        checkingAccount.addCheckingAccountActivity(activity);
        cardActivityRepository.save(activity);
    }

}
