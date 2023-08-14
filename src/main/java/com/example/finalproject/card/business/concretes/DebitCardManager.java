package com.example.finalproject.card.business.concretes;

import com.example.finalproject.account.business.concretes.CheckingAccountManager;
import com.example.finalproject.card.core.exception.debitCardExceptions.*;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.card.repository.DebitCardRepository;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotFoundCheckingAccountException;
import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.card.business.abstracts.DebitCardService;

import com.example.finalproject.card.core.converter.DebitCardConverter;
import com.example.finalproject.card.core.request.CreateDebitCardRequest;
import com.example.finalproject.card.core.response.DebitCardDTO;
import com.example.finalproject.card.entity.DebitCard;
import com.example.finalproject.card.rules.CardRules;

import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.customer.entity.Customer;
import com.example.finalproject.customer.repository.CustomerRepository;
import com.example.finalproject.response.ServiceResponse;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DebitCardManager implements DebitCardService {

    private final DebitCardRepository debitCardRepository;

    private final CheckingAccountManager checkingAccountManager;
    private final CustomerRepository customerRepository;

    private final DebitCardConverter debitCardConverter;

    private final CardRules cardRules;


    public List<DebitCard> getAllDebitCards() {
        List<DebitCard> debitCards = debitCardRepository.findAll();
        return debitCards;
    }
    @Override
    @Transactional
    public ServiceResponse add(long customerId, CreateDebitCardRequest createDebitCardRequest) throws NotFoundCheckingAccountException, AccountAlreadyHasADebitCardException, CustomerNotFoundException, NotAccountOwnerException {
        Instant currentTime = Instant.now();
        cardRules.AlreadyAccountHasADebitCard(createDebitCardRequest.getCheckingAccountId());
        CheckingAccount checkingAccount = checkingAccountManager.findByIdCheckingAccount(createDebitCardRequest.getCheckingAccountId());
        if (checkingAccount == null) {
            throw new NotFoundCheckingAccountException();
        }
        if (checkingAccount.getCustomer().getId() != customerId) {
            throw new NotAccountOwnerException();
        }
        // Kart numarası ve IBAN'ların eşsiz olması için setler oluşturuyoruz
        Set<String> cardNumbers = new HashSet<>();
        Set<String> ibans = new HashSet<>();

        DebitCard debitCard;
        do {
            debitCard = createDebitCard(createDebitCardRequest);
            // Eğer kart numarası veya IBAN zaten sette varsa, yeni kart oluşturulacak
        } while (!cardNumbers.add(debitCard.getCardNumber()) || !ibans.add(debitCard.getIban()));

        debitCard.setCheckingAccount(checkingAccount);
        checkingAccount.setDebitCard(debitCard);
        debitCardRepository.save(debitCard);
        String description = "debit card  oluşturuldu";

        addActivity(description, currentTime, null, null, debitCard);
        return new ServiceResponse("kayıt başarılı", true);
    }

    public void addActivity(String description, Instant currentTime, String crossAccount, BigDecimal amount, DebitCard debitCard) {
        Activity activity = new Activity();
        activity.setAmount(amount);
        activity.setDescription(description);
        activity.setProcessDate(currentTime);
        activity.setCrossAccount(crossAccount);
        debitCard.addCardActivity(activity);
        CheckingAccount checkingAccount = debitCard.getCheckingAccount();
        checkingAccount.addCheckingAccountActivity(activity);
    }


    @Override
    public List<DebitCardDTO> getAll() {
        List<DebitCard> debitCards = debitCardRepository.findAll();
        List<DebitCardDTO> debitCardDTOS = debitCards.stream().filter(account -> account.isActive())
                .map(account -> debitCardConverter.DebitCardDTO(account)).collect(Collectors.toList());
        return debitCardDTOS;
    }


    @Override
    @Transactional
    public ServiceResponse delete(long customerId, long debitCardId ) throws NotFoundDebitCardException, ClientIsAlreadyActiveDebitCard, CustomerNotFoundException, NotCardOwnerException {
        DebitCard debitCard = debitCardRepository.findById(debitCardId);
        Customer customer = customerRepository.findByCustomerId(customerId);
        List<DebitCard> debitCards = new ArrayList<>();
        for (CheckingAccount checkingAccount : customer.getCheckingAccounts()) {
            debitCards.add(checkingAccount.getDebitCard());
        }
        if (!debitCards.contains(debitCard)) {
            throw new NotCardOwnerException();
        }
        if (!debitCard.isActive()) {
            throw new ClientIsAlreadyActiveDebitCard();
        }
        debitCard.setActive(false);
        return new ServiceResponse("silme işlemi başarılı", true);
    }

    @Override
    public List<Activity> getAllActivity(long customerId,long debitCardId) throws NotFoundDebitCardException, CustomerNotFoundException, NotCardOwnerException {
        Customer customer=customerRepository.findByCustomerId(customerId);
        DebitCard debitCard = debitCardRepository.findById(debitCardId);

        List<DebitCard> debitCards = new ArrayList<>();

        for (CheckingAccount checkingAccount : customer.getCheckingAccounts()) {
            debitCards.add(checkingAccount.getDebitCard());
        }
        if (!debitCards.contains(debitCard)) {
            throw new NotCardOwnerException();
        }
        List<Activity> activities = debitCard.getActivityList();
        return activities;
    }

    private DebitCard createDebitCard(CreateDebitCardRequest createDebitCardRequest) throws NotFoundCheckingAccountException {
        CheckingAccount checkingAccount = checkingAccountManager.findByIdCheckingAccount(createDebitCardRequest.getCheckingAccountId());
        String cvvNumber = "";
        String cardNumber = generateCardNumber();

        for (int i = 0; i < 3; i++) {
            int randomDigit = generateRandomDigit();
            cvvNumber += randomDigit;
        }

        LocalDate now = LocalDate.now();
        LocalDate future = now.plusYears(5);
        LocalDate expiryDate = future.withDayOfMonth(1);

        DebitCard debitCard = new DebitCard();
        debitCard.setCardNumber(cardNumber);
        debitCard.setNameAndSurname(checkingAccount.getAccountHolderName());
        debitCard.setIban(checkingAccount.getIban());
        debitCard.setExpiryDate(expiryDate);
        debitCard.setCvv(cvvNumber);
        debitCard.setPassword(createDebitCardRequest.getPassword());
        debitCard.setActive(true);
        debitCard.setBalance(checkingAccount.getBalance());
        debitCard.setLockedBalance(checkingAccount.getLockedBalance());

        return debitCard;
    }

    private String generateCardNumber() {
        String cardNumber = "";
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int randomDigit = random.nextInt(10);
                cardNumber += randomDigit;
            }
            if (i < 3) {
                cardNumber += " ";
            }
        }

        return cardNumber;
    }

    private int generateRandomDigit() {
        Random random = new Random();
        return random.nextInt(10);
    }


}
