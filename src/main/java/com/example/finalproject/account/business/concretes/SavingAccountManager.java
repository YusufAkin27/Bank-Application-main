package com.example.finalproject.account.business.concretes;

import com.example.finalproject.account.business.abstracts.SavingAccountService;
import com.example.finalproject.account.core.converter.SavingAccountConverter;
import com.example.finalproject.account.core.exception.savingAccountException.NotFoundSavingAccountException;
import com.example.finalproject.account.core.exception.savingAccountException.isBaseSavingAccountException;
import com.example.finalproject.account.core.request.AddMoneySavingAccount;
import com.example.finalproject.account.core.response.SavingAccountDTO;
import com.example.finalproject.account.entity.SavingAccount;
import com.example.finalproject.account.entity.enums.Maturity;
import com.example.finalproject.account.repository.SavingAccountRepository;
import com.example.finalproject.account.core.request.CreateSavingAccountRequest;

import com.example.finalproject.account.entity.enums.AccountType;


import com.example.finalproject.card.core.exception.debitCardExceptions.NotAccountOwnerException;
import com.example.finalproject.card.entity.base.Activity;

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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SavingAccountManager implements SavingAccountService {
    private final SavingAccountRepository savingAccountRepository;

    private final CustomerRepository customerRepository;

    private final SavingAccountConverter savingAccountConverter;


    Instant currentTime = Instant.now();

    @Override
    @Transactional
    public ServiceResponse add(long customerId, CreateSavingAccountRequest createSavingAccountRequest) throws CustomerNotFoundException, isBaseSavingAccountException, NotAccountOwnerException {
        SavingAccount savingAccount = createToSavingAccount(customerId, createSavingAccountRequest);
        if (savingAccount.getCustomer().getId() != customerId) {
            throw new NotAccountOwnerException();
        }
        savingAccountRepository.save(savingAccount);
        String description = "koruma hesabı açıldı";
        addActivity(description, currentTime, "", null, savingAccount);
        return new ServiceResponse("koruma hesabınız başarılı bir şekilde eklendi.", true);

    }

    @Override
    @Transactional
    public ServiceResponse delete(long customerId, long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException {
        SavingAccount savingAccount = findByIdSavingAccount(savingAccountId);
        if (savingAccount.getCustomer().getId() != customerId) {
            throw new NotAccountOwnerException();
        }
        savingAccount.setActive(false);
        String description = "koruma hesabınız silindi";
        addActivity(description, currentTime, "", null, savingAccount);
        return new ServiceResponse("koruma hesabını silme işlemi başarılı.", true);

    }

    @Override
    public List<Activity> getActivities(long customerId, long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException {
        SavingAccount savingAccount = findByIdSavingAccount(savingAccountId);
        if (savingAccount.getCustomer().getId() != customerId) {
            throw new NotAccountOwnerException();
        }

        List<Activity> activityList = savingAccount.getAccountActivities();
        return activityList;
    }

    @Transactional
    public void addActivity(String description, Instant currentTime, String crossAccount, BigDecimal amount, SavingAccount savingAccount) {
        Activity activity = new Activity();
        activity.setAmount(amount);
        activity.setDescription(description);
        activity.setProcessDate(currentTime);
        activity.setCrossAccount(crossAccount);
        savingAccount.addAccountActivity(activity);
    }

    @Override
    public List<SavingAccountDTO> getAll() {
        List<SavingAccount> savingAccounts = savingAccountRepository.findAll();

        List<SavingAccountDTO> savingAccountDTOS = savingAccounts.stream()
                .map(savingAccount -> {
                    BigDecimal unlockedBalance = updateBalance(savingAccount);
                    // SavingAccountDTO nesnesini oluşturun ve döndürün
                    SavingAccountDTO dto = savingAccountConverter.toDTO(savingAccount);
                    dto.setLockedBalance(unlockedBalance.setScale(2, RoundingMode.HALF_UP)); // Yüzdeyi virgülden sonra 2 rakama yuvarlayarak ayarlayın
                    return dto;
                })
                .collect(Collectors.toList());

        return savingAccountDTOS;
    }


    public BigDecimal updateBalance(SavingAccount savingAccount) {
        BigDecimal interestRate = getInterestRate(savingAccount.getMaturity());
        int maturityDays = savingAccount.getMaturity().getValue();
        BigDecimal unlockedBalance = calculateUnlockedBalancePerMinute(savingAccount.getLockedBalance(), interestRate, maturityDays);
        return unlockedBalance;
    }

    @Override
    @Transactional
    public ServiceResponse addMoney(long customerId, AddMoneySavingAccount addMoneySavingAccount) throws NotFoundSavingAccountException, NotAccountOwnerException {
        SavingAccount savingAccount = findByIdSavingAccount(addMoneySavingAccount.getSavingAccountId());
        if (savingAccount.getCustomer().getId() != customerId) {
            throw new NotAccountOwnerException();
        }

        updateAccountWithDeposit(savingAccount, addMoneySavingAccount.getAmount(), addMoneySavingAccount.getMaturity());

        String description = "para yatırma işlemi başarılı.";
        addActivity(description, currentTime, "", addMoneySavingAccount.getAmount(), savingAccount);

        return new ServiceResponse("para yatırma işlemi başarılı", true);
    }

    private void updateAccountWithDeposit(SavingAccount savingAccount, BigDecimal depositAmount, Maturity maturity) {
        BigDecimal interestRate = getInterestRate(maturity);
        int maturityDays = maturity.getValue();

        BigDecimal unlockedBalance = calculateUnlockedBalancePerMinute(depositAmount, interestRate, maturityDays);
        savingAccount.setLockedBalance(savingAccount.getLockedBalance().add(depositAmount));
        savingAccount.setMaturity(maturity);
        LocalDate maturityDate = LocalDate.now().plusDays(maturityDays);
        savingAccount.setMaturityDate(maturityDate);
        scheduleUnlockedBalanceUpdate(savingAccount);

        BigDecimal targetAmount = savingAccount.getTargetAmount();
        if (targetAmount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal targetPercentage = savingAccount.getBalance().multiply(BigDecimal.valueOf(100)).divide(targetAmount, 2, RoundingMode.HALF_UP);
            savingAccount.setSuccessRate(targetPercentage.setScale(2, RoundingMode.HALF_UP)); // Yüzdeyi virgülden sonra 2 rakama yuvarlayarak ayarlayın
        } else {
            savingAccount.setSuccessRate(BigDecimal.ZERO);
        }
    }


    @Transactional
    private void scheduleUnlockedBalanceUpdate(SavingAccount savingAccount) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            BigDecimal interestRate = getInterestRate(savingAccount.getMaturity());
            int maturityDays = savingAccount.getMaturity().getValue();
            BigDecimal unlockedBalance = calculateUnlockedBalancePerMinute(savingAccount.getLockedBalance(), interestRate, maturityDays);
            savingAccount.setBalance(savingAccount.getBalance().add(unlockedBalance));
        }, 0, 1, TimeUnit.MINUTES); // Her dakika güncelleme yapacak
        //her dakika güncellendiğinden performans sorunu yaşanabiliyor bunu geceleri işlem sıklığının
        // en az zamanı olduğu zamanlar güncelleyebiliriz mesela 03.00 - 05.00 arası
        executorService.shutdown();
    }

    private static BigDecimal calculateUnlockedBalancePerMinute(BigDecimal lockedBalance, BigDecimal interestRate, int maturityDays) {
        BigDecimal unlockedBalance = lockedBalance;
        BigDecimal minuteInterestRate = interestRate.divide(new BigDecimal("100")).divide(new BigDecimal(String.valueOf(maturityDays * 24 * 60)), 12, BigDecimal.ROUND_HALF_UP);

        for (int minute = 1; minute <= maturityDays * 24 * 60; minute++) {
            BigDecimal minuteInterest = unlockedBalance.multiply(minuteInterestRate);
            unlockedBalance = unlockedBalance.add(minuteInterest);
        }

        return unlockedBalance;
    }

    private BigDecimal getInterestRate(Maturity maturity) {
        BigDecimal interestRate;
        switch (maturity) {
            case OTUZ_GUN:
                interestRate = new BigDecimal("1.2");
                break;
            case ALTMIS_GUN:
                interestRate = new BigDecimal("1.4");
                break;
            case DOKSAN_GUN:
                interestRate = new BigDecimal("1.6");
                break;
            case YUZ_SEKSEN_GUN:
                interestRate = new BigDecimal("2.0");
                break;
            default:
                interestRate = BigDecimal.ZERO;
        }
        return interestRate;
    }


    @Override
    @Transactional
    public ServiceResponse doActive(long customerId, long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException {
        SavingAccount savingAccount = findByIdSavingAccount(savingAccountId);
        if (savingAccount.getCustomer().getId() != customerId) {
            throw new NotAccountOwnerException();
        }
        if (savingAccount.isActive()) {
            return new ServiceResponse("Hesabınız zaten aktif.", false);

        }
        savingAccount.setActive(true);

        return new ServiceResponse("Hesabınız aktive edildi.", true);

    }


    public SavingAccount findByIdSavingAccount(long savingAccountId) throws NotFoundSavingAccountException {
        List<SavingAccount> savingAccounts = savingAccountRepository.findAll();
        SavingAccount savingAccount = savingAccounts.stream().filter(savingAccount1 -> savingAccount1.getId() == savingAccountId).findFirst().orElse(null);
        if (savingAccount == null) {
            throw new NotFoundSavingAccountException();
        }

        return savingAccount;
    }

    public SavingAccount createToSavingAccount(long customerId, CreateSavingAccountRequest createSavingAccountRequest) throws CustomerNotFoundException, isBaseSavingAccountException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        if (!customer.getSavingAccounts().isEmpty()) {
            throw new isBaseSavingAccountException();
        }
        String ibanNo = "";
        String accountNo = "";
        Random random = new Random();
        int rakam;
        for (int i = 0; i < 16; i++) {
            rakam = random.nextInt(10);
            accountNo += rakam;
        }
        for (int i = 0; i < 26; i++) {
            rakam = random.nextInt(10);
            ibanNo += rakam;
        }
        ibanNo = "TR" + ibanNo;

        LocalDate now = LocalDate.now();
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setLockedBalance(createSavingAccountRequest.getLockedBalance());
        savingAccount.setAccountNumber(accountNo);
        savingAccount.setPurposeSaving(createSavingAccountRequest.getPurposeSaving());
        savingAccount.setAccountHolderName(customer.getName() + " " + customer.getSurname());
        savingAccount.setAccountActivities(null);
        savingAccount.setTargetAmount(createSavingAccountRequest.getTargetAmount());
        savingAccount.setCreatedAt(now);
        savingAccount.setIban(ibanNo);
        savingAccount.setSuccessRate(BigDecimal.ZERO);
        savingAccount.setAccountType(AccountType.SAVING);
        savingAccount.setCustomer(customer);
        return savingAccount;
    }


}

