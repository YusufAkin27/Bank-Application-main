package com.example.finalproject.account.controller;

import com.example.finalproject.account.business.abstracts.SavingAccountService;
import com.example.finalproject.account.core.exception.savingAccountException.NotFoundSavingAccountException;
import com.example.finalproject.account.core.exception.savingAccountException.isBaseSavingAccountException;
import com.example.finalproject.account.core.request.AddMoneySavingAccount;
import com.example.finalproject.account.core.request.CreateSavingAccountRequest;

import com.example.finalproject.account.core.response.SavingAccountDTO;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotAccountOwnerException;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.response.DataServiceResponse;
import com.example.finalproject.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/accounts/savingAccounts")
@RequiredArgsConstructor
public class SavingAccountController {

    private final SavingAccountService savingAccountService;

    @PostMapping("/add")
    public ServiceResponse add(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                               @RequestBody CreateSavingAccountRequest createSavingAccountRequest) throws CustomerNotFoundException, isBaseSavingAccountException, NotAccountOwnerException {
        return savingAccountService.add(userDetail.getUser().getId(), createSavingAccountRequest);
    }

    @DeleteMapping("/delete")
    public ServiceResponse delete(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                  @RequestParam(name = "savingAccountId") long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException {
        return savingAccountService.delete(userDetail.getUser().getId(), savingAccountId);
    }

    @GetMapping("/getActivities")
    public List<Activity> getActivities(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                        @RequestParam(name = "savingAccountId") long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException {
        return savingAccountService.getActivities(userDetail.getUser().getId(), savingAccountId);
    }

    @GetMapping("/getall")
    public List<SavingAccountDTO> getAll() {
        return savingAccountService.getAll();
    }

    @PostMapping("/addmoney")
    public ServiceResponse addMoney(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                    @RequestBody AddMoneySavingAccount addMoneySavingAccount) throws NotFoundSavingAccountException, NotAccountOwnerException {
        return savingAccountService.addMoney(userDetail.getUser().getId(), addMoneySavingAccount);
    }

    @GetMapping("/doactive")
    public ServiceResponse doActive(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                    @RequestParam(name = "savingAccountId") long savingAccountId) throws NotFoundSavingAccountException, NotAccountOwnerException {
        return savingAccountService.doActive(userDetail.getUser().getId(), savingAccountId);
    }

}
