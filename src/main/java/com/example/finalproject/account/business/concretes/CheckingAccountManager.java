package com.example.finalproject.account.business.concretes;


import com.example.finalproject.account.business.abstracts.CheckingAccountService;
import com.example.finalproject.account.core.converter.CheckingAccountConverter;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.AddressCannotBeEmptyException;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.CheckingAccountAlreadyActiveException;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotCustomerOwnerException;
import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotFoundCheckingAccountException;
import com.example.finalproject.account.core.response.CheckingAccountDTO;
import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.account.repository.CheckingAccountRepository;
import com.example.finalproject.account.rules.CheckingAccountRules;

import com.example.finalproject.account.core.request.CreateCheckingAccountRequest;
import com.example.finalproject.account.entity.enums.AccountType;


import com.example.finalproject.card.core.exception.debitCardExceptions.NotAccountOwnerException;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.card.repository.CardActivityRepository;

import com.example.finalproject.customer.core.exception.CustomerNotActiveException;
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
public class CheckingAccountManager implements CheckingAccountService {

    private final CheckingAccountRules checkingAccountRules;

    private final CheckingAccountConverter checkingAccountConverter;

    private final CustomerRepository customerRepository;

    private final CheckingAccountRepository checkingAccountRepository;

    private final CardActivityRepository cardActivityRepository;

    Instant currentTime = Instant.now();


    @Override
    @Transactional
    public ServiceResponse add(long customerId, CreateCheckingAccountRequest createCheckingAccountRequest) throws CustomerNotFoundException, AddressCannotBeEmptyException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        checkingAccountRules.addressNotEmpty(customer);
        CheckingAccount checkingAccount = CreateAccountConverter(customerId, createCheckingAccountRequest);
        checkingAccountRepository.save(checkingAccount);
        String description = "Kontrol Hesabı Oluşturuldu";
        addActivity(description, currentTime, "", null, checkingAccount);
        return new ServiceResponse("Hesap oluşturma işlemi başarılı", true);

    }


    @Override
    @Transactional
    public ServiceResponse delete(long customerId, long checkingAccountId) throws NotFoundCheckingAccountException, CustomerNotFoundException, NotCustomerOwnerException, CustomerNotActiveException {
        CheckingAccount checkingAccount = findByIdCheckingAccount(checkingAccountId);
        Customer customer=customerRepository.findByCustomerId(customerId);
        if (checkingAccount.getCustomer().getId()!=customerId) {
            throw new NotCustomerOwnerException();
        }
        if (!customer.isActive()) {
            throw new CustomerNotActiveException();
        }
        checkingAccount.setActive(false);
        String description = "Kontrol Hesabı Kapatıldı";
        addActivity(description, currentTime, "", null, checkingAccount);
        return new ServiceResponse(checkingAccount+"'ID li kontrol hesabı kapatıldı.", true);

    }

    @Transactional
    public void addActivity(String description, Instant currentTime, String crossAccount, BigDecimal amount, CheckingAccount checkingAccount) {
        Activity activity = new Activity();
        activity.setAmount(amount);
        activity.setDescription(description);
        activity.setProcessDate(currentTime);
        activity.setCrossAccount(crossAccount);
        checkingAccount.addCheckingAccountActivity(activity);
        cardActivityRepository.save(activity);
    }

    @Override
    @Transactional
    public ServiceResponse activate(long customerId, long checkingAccountId) throws NotFoundCheckingAccountException, CheckingAccountAlreadyActiveException, NotAccountOwnerException {
        CheckingAccount checkingAccount = findByIdCheckingAccount(checkingAccountId);
        Customer customer = checkingAccount.getCustomer();
        if (customer.getId() != customerId) {
            throw new NotAccountOwnerException();
        }

        if (checkingAccount.isActive()) {
            throw new CheckingAccountAlreadyActiveException();
        }
        checkingAccount.setActive(true);
        String description = "Kontrol Hesabı aktifleştirildi";
        addActivity(description, currentTime, "", null, checkingAccount);
        return new ServiceResponse("Hesap Aktfileştirildi", true);

    }


    @Override
    public List<CheckingAccountDTO> getAllWithPagination(int page, int pageSize) {
        List<CheckingAccount> checkingAccounts = checkingAccountRepository.findAll();

        int totalAccounts = checkingAccounts.size();

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalAccounts);

        List<CheckingAccount> accountsOnPage = checkingAccounts.subList(startIndex, endIndex);

        List<CheckingAccountDTO> checkingAccountDTOS = new ArrayList<>();
        for (CheckingAccount account : accountsOnPage) {
            CheckingAccountDTO accountDTO = checkingAccountConverter.toDTO(account);
            checkingAccountDTOS.add(accountDTO);
        }
        return checkingAccountDTOS;
    }

    @Override
    public List<CheckingAccountDTO> searchAccount(String info) throws NotFoundCheckingAccountException {
        List<CheckingAccount> accounts = checkingAccountRepository.searchAccount(info);

        if (accounts.isEmpty()) {
            throw new NotFoundCheckingAccountException();
        }

        List<CheckingAccountDTO> accountDTOS = accounts.stream()
                .map(account -> checkingAccountConverter.toDTO(account))
                .collect(Collectors.toList());

        return accountDTOS;
    }

    @Override
    public List<Activity> activateWithPagination(long customerId, long checkingAccountId, int page, int pageSize) throws  NotAccountOwnerException {
        CheckingAccount checkingAccount = checkingAccountRepository.getById(checkingAccountId);
        List<Activity> allActivities = checkingAccount.getAccountActivities();
        if (checkingAccount.getCustomer().getId() != customerId) {
            throw new NotAccountOwnerException();
        }
        int totalActivities = allActivities.size();

        allActivities.sort(Comparator.comparing(Activity::getProcessDate).reversed());

        List<Activity> pageActivities = new ArrayList<>();
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalActivities);

        for (int i = startIndex; i < endIndex; i++) {
            pageActivities.add(allActivities.get(i));
        }

        return pageActivities;
    }

    public CheckingAccount findByIdCheckingAccount(long checkingAccountId) throws NotFoundCheckingAccountException {
        List<CheckingAccount> checkingAccounts = checkingAccountRepository.findAll();
        CheckingAccount checkingAccount = checkingAccounts.stream().filter(account -> account.getId() == checkingAccountId).findFirst().orElse(null);
        if (checkingAccount == null) {
            throw new NotFoundCheckingAccountException();
        }
        return checkingAccount;
    }


    public CheckingAccount CreateAccountConverter(long customerId, CreateCheckingAccountRequest createCheckingAccountRequest) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        String ibanNo = "";
        String accountNo = "";
        Random random = new Random();
        int rakam;
        for (int i = 0; i < 16; i++) {
            rakam = random.nextInt(10);
            accountNo += rakam;
        }
        LocalDate now = LocalDate.now();
        ibanNo = "TR" + createCheckingAccountRequest.getBankCode() + accountNo;
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber(accountNo);
        checkingAccount.setAccountHolderName(customer.getName() + " " + customer.getSurname());
        checkingAccount.setAccountActivities(null);
        checkingAccount.setAccountType(AccountType.CHECKING);
        checkingAccount.setCreatedAt(now);
        checkingAccount.setActive(true);
        checkingAccount.setCustomer(customer);
        checkingAccount.setBankCode(createCheckingAccountRequest.getBankCode());
        checkingAccount.setBranchCode(createCheckingAccountRequest.getBranchCode());
        checkingAccount.setBranchName(createCheckingAccountRequest.getBranchName());
        checkingAccount.setIban(ibanNo);
        customer.setCheckingAccounts(Collections.singletonList(checkingAccount));
        return checkingAccount;
    }
}
