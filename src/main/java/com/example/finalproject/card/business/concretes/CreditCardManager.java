package com.example.finalproject.card.business.concretes;


import com.example.finalproject.atm.core.WrongPasswordException;
import com.example.finalproject.card.business.abstracts.CreditCardService;
import com.example.finalproject.card.core.converter.CreditCardConverter;
import com.example.finalproject.card.core.exception.creditCardExceptions.*;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotCardOwnerException;
import com.example.finalproject.card.core.request.DepositMoneyRequest;
import com.example.finalproject.card.core.request.WithdrawalMoneyRequest;
import com.example.finalproject.card.core.response.CreditCardDTO;
import com.example.finalproject.card.entity.CreditCard;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.card.repository.CardActivityRepository;
import com.example.finalproject.card.repository.CreditCardRepository;
import com.example.finalproject.card.core.request.CreateCreditCardRequest;
import com.example.finalproject.card.rules.CardRules;

import com.example.finalproject.customer.core.exception.CustomerNotActiveException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.customer.entity.Customer;
import com.example.finalproject.customer.repository.CustomerRepository;
import com.example.finalproject.response.DataServiceResponse;

import com.example.finalproject.response.ServiceResponse;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditCardManager implements CreditCardService {

    private final CustomerRepository customerRepository;

    private final CreditCardRepository creditCardRepository;

    private final CreditCardConverter creditCardConverter;

    private final CardRules cardRules;

    private final CardActivityRepository activityRepository;


    Instant currentTime = Instant.now();

    @Override
    @Transactional
    public DataServiceResponse<CreditCard> add(long customerId, String password) throws CustomerNotFoundException, CreateCreditCardException {
        CreditCard card = createCreditCard(customerId, password);

        while (cardRules.isCardNumberUnique(card.getCardNumber()) || cardRules.isIbanUnique(card.getIban())) {
            card = createCreditCard(customerId, password);
        }

        if (card == null) {
            throw new CreateCreditCardException();
        }

        String description = "Kredi kartı hesabınız oluşturuldu.";

        addActivity(description, currentTime, "", null, card);
        creditCardRepository.save(card);
        return new DataServiceResponse<>(card);
    }

    @Override
    @Transactional
    public ServiceResponse doActive(long creditCardId, long customerId) throws
            NotFoundCreditCardException, AlreadyActiveCreditCardException, CustomerNotFoundException, ThisCreditCardIsNotYoursException, CustomerNotActiveException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        CreditCard creditCard = creditCardFindById(creditCardId);
        if (!customer.getCreditCards().contains(creditCard) && creditCard.getCustomer().getId() == customerId) {
            throw new ThisCreditCardIsNotYoursException();
        }

        if (!customer.isActive()) {
            throw new CustomerNotActiveException();
        }
        if (creditCard.isActive()) {
            throw new AlreadyActiveCreditCardException();
        }

        creditCard.setActive(true);
        String description = "Kredi kartınız yeniden etkinleştirildi.";

        addActivity(description, currentTime, "", null, creditCard);

        return new ServiceResponse("Kredi kartı artık aktif.", true);

    }

    public List<CreditCard> getAllCreditCards() {
        List<CreditCard> creditCards = creditCardRepository.findAll();
        return creditCards;
    }

    @Override
    public CreditCard getIdentityNumber(String identityNu) throws NotFoundCreditCardException {
        CreditCard creditCard = findIdentityNumber(identityNu);
        return creditCard;
    }

    @Override
    @Transactional
    public ServiceResponse delete(long creditCardId, long customerId) throws NotFoundCreditCardException, CreditCardStillHasDebtException, CustomerNotFoundException, NotCardOwnerException {
        CreditCard creditCard = creditCardFindById(creditCardId);
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (!customer.getCreditCards().contains(creditCard)) {
            throw new NotCardOwnerException();
        }
        creditCard.setActive(false);
        if (!creditCard.getDebt().equals(BigDecimal.ZERO)) {
            throw new CreditCardStillHasDebtException();
        }
        String description = "Kredi kartı işlemleri devre dışı bırakıldı.";

        addActivity(description, currentTime, "", null, creditCard);
        return new ServiceResponse("Kredi kartı silindi.", true);


    }

    @Override
    public List<CreditCardDTO> getAll() {
        List<CreditCard> creditCards = creditCardRepository.findAll();
        List<CreditCardDTO> creditCardDTOS = creditCards.stream().map(creditCard -> creditCardConverter.toDTO(creditCard)).collect(Collectors.toList());
        return creditCardDTOS;
    }

    @Transactional
    public void addActivity(String description, Instant currentTime, String crossAccount, BigDecimal amount, CreditCard creditCard) {
        Activity activity = new Activity();
        activity.setAmount(amount);
        activity.setDescription(description);
        activity.setProcessDate(currentTime);
        activity.setCrossAccount(crossAccount);
        creditCard.addCardActivity(activity);
        activityRepository.save(activity);

    }

    @Override
    @Transactional
    public CreditCardDTO withdrawal(long customerId, WithdrawalMoneyRequest withdrawalMoneyRequest) throws NotFoundCreditCardException, WrongPasswordException, LimitException, MoreMonthException, CustomerNotFoundException, NotCardOwnerException {

        CreditCard creditCard = creditCardFindById(withdrawalMoneyRequest.getCreditCardId());
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (!customer.getCreditCards().contains(creditCard)) {
            throw new NotCardOwnerException();
        }

        cardRules.withdrawalMoney(creditCard, withdrawalMoneyRequest.getAmount());
        if (withdrawalMoneyRequest.getMonth() > 12) {
            throw new MoreMonthException();
        }
        if (creditCard.getPassword().equals(withdrawalMoneyRequest.getPassword())) {
            double faiz = 1.3;
            BigDecimal debt = withdrawalMoneyRequest.getAmount()
                    .multiply(BigDecimal.valueOf(withdrawalMoneyRequest.getMonth()))
                    .multiply(BigDecimal.valueOf(faiz));

            creditCard.setDebt(debt);
            creditCard.setMonthlyDebt(debt.divide(BigDecimal.valueOf(withdrawalMoneyRequest.getMonth()), RoundingMode.HALF_UP));
        } else {
            throw new WrongPasswordException();
        }


        String description = ("para çekildi" + withdrawalMoneyRequest.getAmount() + "kredi kartındaki tutar.");

        addActivity(description, currentTime, "", withdrawalMoneyRequest.getAmount(), creditCard);
        return creditCardConverter.toDTO(creditCard);
    }

    @Override
    @Transactional
    public CreditCardDTO deposit(long customerId, DepositMoneyRequest depositMoneyRequest) throws
            NotFoundCreditCardException, WrongPasswordException, CustomerNotFoundException, NotCardOwnerException {
        CreditCard creditCard = creditCardFindById(depositMoneyRequest.getCreditCardId());
        Customer customer = customerRepository.findByCustomerId(customerId);

        if (!customer.getCreditCards().contains(creditCard)) {
            throw new NotCardOwnerException();
        }
        if (!creditCard.getPassword().equals(depositMoneyRequest.getPassword())) {
            throw new WrongPasswordException();
        }

        BigDecimal depositAmount = depositMoneyRequest.getAmount();

        BigDecimal monthlyDebt = creditCard.getMonthlyDebt();
        BigDecimal totalDebt = creditCard.getDebt();

        // Para önce aylık borçtan kesilir
        if (depositAmount.compareTo(monthlyDebt) >= 0) {
            depositAmount = depositAmount.subtract(monthlyDebt);
            creditCard.setMonthlyDebt(BigDecimal.ZERO);
        } else {
            creditCard.setMonthlyDebt(monthlyDebt.subtract(depositAmount));
            depositAmount = BigDecimal.ZERO;
        }

        // Eğer daha fazla para yatırıldıysa toplam borçtan da düşülür
        if (depositAmount.compareTo(totalDebt) >= 0) {
            creditCard.setDebt(BigDecimal.ZERO);
        } else {
            creditCard.setDebt(totalDebt.subtract(depositAmount));
        }

        String description = ("para yatırıldı " + depositMoneyRequest.getAmount() + " kredi kartındaki tutar");

        addActivity(description, currentTime, "", depositMoneyRequest.getAmount(), creditCard);
        return creditCardConverter.toDTO(creditCard);
    }

    @Override
    public List<Activity> getAllActivity(long customerId, long creditCardId) throws NotFoundCreditCardException, CustomerNotFoundException, NotCardOwnerException {
        CreditCard creditCard = creditCardFindById(creditCardId);
        Customer customer = customerRepository.findByCustomerId(customerId);

        if (!customer.getCreditCards().contains(creditCard)) {
            throw new NotCardOwnerException();
        }
        List<Activity> activities = creditCard.getActivityList();
        return activities;
    }


    public CreditCard findIdentityNumber(String identityNumber) throws NotFoundCreditCardException {
        CreditCard creditCard = creditCardRepository.findByCustomer_IdentityNumber(identityNumber);
        if (creditCard == null) {
            throw new NotFoundCreditCardException();
        }
        return creditCard;
    }

    public CreditCard creditCardFindById(long creditCardId) throws NotFoundCreditCardException {
        CreditCard creditCard = creditCardRepository.findById(creditCardId);
        if (creditCard == null) {
            throw new NotFoundCreditCardException();
        }
        return creditCard;
    }

    @Transactional
    private CreditCard createCreditCard(long customerId, String password) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        String cvvNumber = "";
        String cardNumber = generateCardNumber();
        String iban = generateIban();

        LocalDate now = LocalDate.now();
        LocalDate future = now.plusYears(5);
        LocalDate expiryDate = future.withDayOfMonth(1);


        for (int i = 0; i < 3; i++) {
            int randomDigit = generateRandomDigit();
            cvvNumber += randomDigit;
        }

        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(cardNumber);
        creditCard.setNameAndSurname(customer.getName() + " " + customer.getSurname());
        creditCard.setIban(iban);
        creditCard.setExpiryDate(expiryDate);
        creditCard.setCvv(cvvNumber);
        creditCard.setPassword(password);
        creditCard.setActive(true);
        creditCard.setBalance(BigDecimal.ZERO);
        creditCard.setCustomer(customer);
        creditCard.setDebt(BigDecimal.ZERO);
        creditCard.setMonthlyDebt(BigDecimal.ZERO);

        return creditCard;
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

    private String generateIban() {
        String iban = "TR";
        Random random = new Random();

        for (int i = 0; i < 24; i++) {
            int digit = random.nextInt(10);
            iban += digit;
        }

        return iban;
    }

    private int generateRandomDigit() {
        Random random = new Random();
        return random.nextInt(10);
    }

}
