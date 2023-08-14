package com.example.finalproject.card.controller;

import com.example.finalproject.account.core.exception.checkingAccountExcepiton.NotFoundCheckingAccountException;
import com.example.finalproject.card.business.abstracts.DebitCardService;

import com.example.finalproject.card.core.exception.creditCardExceptions.WrongCardPasswordException;
import com.example.finalproject.card.core.exception.debitCardExceptions.*;
import com.example.finalproject.card.core.request.CreateDebitCardRequest;
import com.example.finalproject.card.core.response.DebitCardDTO;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/cards/debitCard")
@RequiredArgsConstructor
public class DebitCardController {

    private final DebitCardService debitCardService;

    @PostMapping("/add")
    public ServiceResponse add(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                               @RequestBody CreateDebitCardRequest createDebitCardRequest) throws NotFoundCheckingAccountException,
            CreateNotDebitCardException, AccountAlreadyHasADebitCardException, CustomerNotFoundException, NotAccountOwnerException, WrongCardPasswordException {
        if (createDebitCardRequest.getPassword().length() == 3) {
            throw new WrongCardPasswordException();
        }
        return debitCardService.add(userDetail.getUser().getId(), createDebitCardRequest);
    }

    @GetMapping("/getAll")
    public List<DebitCardDTO> getAll() {
        return debitCardService.getAll();
    }

    @DeleteMapping("/delete")
    public ServiceResponse delete(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                  @PathVariable(name = "debitCardId") long debitCardId) throws NotFoundDebitCardException,
            ClientIsAlreadyActiveDebitCard, NotCardOwnerException, CustomerNotFoundException {
        return debitCardService.delete(userDetail.getUser().getId(), debitCardId);
    }

    @GetMapping("/getAllActivity")
    public List<Activity> getAllActivity(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                         @RequestParam(name = "debitCardId") long debitCardId) throws NotFoundDebitCardException, NotCardOwnerException, CustomerNotFoundException {
        return debitCardService.getAllActivity(userDetail.getUser().getId(), debitCardId);
    }
}
