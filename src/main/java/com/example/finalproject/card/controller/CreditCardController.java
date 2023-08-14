package com.example.finalproject.card.controller;

import com.example.finalproject.atm.core.WrongPasswordException;
import com.example.finalproject.card.core.exception.creditCardExceptions.*;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotCardOwnerException;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.card.core.request.DepositMoneyRequest;
import com.example.finalproject.card.core.request.WithdrawalMoneyRequest;
import com.example.finalproject.card.core.response.CreditCardDTO;
import com.example.finalproject.card.entity.CreditCard;
import com.example.finalproject.card.core.request.CreateCreditCardRequest;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.core.exception.CustomerNotActiveException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;

import com.example.finalproject.card.business.abstracts.CreditCardService;

import com.example.finalproject.response.DataServiceResponse;
import com.example.finalproject.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bank/cards/creditCard")
@RestController
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping("/add")
    public DataServiceResponse<CreditCard> add(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                               @RequestParam(name = "password") String password) throws CreateCreditCardException,
            CustomerNotFoundException, WrongCardPasswordException {
        if (password.length() == 3) {
            throw new WrongCardPasswordException();
        }
        return creditCardService.add(userDetail.getUser().getId(), password);
    }

    @GetMapping("/doactive")
    public ServiceResponse doActive(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail
            , @RequestParam(name = "creditCardId") long creditCardId)
            throws AlreadyActiveCreditCardException, NotFoundCreditCardException, CustomerNotActiveException, ThisCreditCardIsNotYoursException, CustomerNotFoundException {
        return creditCardService.doActive(creditCardId, userDetail.getUser().getId());
    }


    @GetMapping("/getIdentityNu")
    public CreditCard doActive(@RequestParam(name = "identityNu") String identityNu) throws NotFoundCreditCardException {
        return creditCardService.getIdentityNumber(identityNu);
    }

    @DeleteMapping("/delete")
    public ServiceResponse delete(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail
            , @PathVariable(name = "creditCardId") long creditCardId) throws NotFoundCreditCardException, CreditCardStillHasDebtException, NotCardOwnerException, CustomerNotFoundException {
        return creditCardService.delete(creditCardId, userDetail.getUser().getId());
    }

    @GetMapping("/getall")
    public List<CreditCardDTO> getAll() {
        return creditCardService.getAll();
    }

    @PostMapping("/withdrawal")
    public CreditCardDTO withdrawal(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                    @RequestBody WithdrawalMoneyRequest withdrawalMoneyRequest) throws NotFoundCreditCardException, WrongPasswordException, MoreMonthException, LimitException, NotCardOwnerException, CustomerNotFoundException {
        return creditCardService.withdrawal(userDetail.getUser().getId(), withdrawalMoneyRequest);
    }

    @PostMapping("/deposit")
    public CreditCardDTO deposit(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                 @RequestBody DepositMoneyRequest depositMoneyRequest) throws NotFoundCreditCardException, WrongPasswordException, NotCardOwnerException, CustomerNotFoundException {
        return creditCardService.deposit(userDetail.getUser().getId(), depositMoneyRequest);
    }

    @GetMapping("/getAllActivity")
    public List<Activity> getAllActivity(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                         @RequestParam(name = "creditCardId") long creditCardId) throws NotFoundCreditCardException, NotCardOwnerException, CustomerNotFoundException {
        return creditCardService.getAllActivity(userDetail.getUser().getId(), creditCardId);
    }

}
