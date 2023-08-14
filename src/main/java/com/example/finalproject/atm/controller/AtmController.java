package com.example.finalproject.atm.controller;

import com.example.finalproject.atm.business.abstracts.AtmService;
import com.example.finalproject.atm.core.NotFoundCardNumberException;
import com.example.finalproject.atm.core.WrongPasswordException;
import com.example.finalproject.atm.entity.Atm;
import com.example.finalproject.card.core.exception.creditCardExceptions.WrongCardPasswordException;
import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.response.ServiceResponse;
import com.example.finalproject.transfer.exception.InsufficientBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank/atm")
@RequiredArgsConstructor
public class AtmController {

    private final AtmService atmService;
    @PostMapping("/process")
    public ServiceResponse process(@RequestBody Atm atm) throws NotFoundDebitCardException, DebitCardNotActiveException, InsufficientBalanceException, WrongPasswordException, NotFoundCardNumberException, WrongCardPasswordException {
        if (atm.getPassword().length() == 3) {
            throw new WrongCardPasswordException();
        }
        return atmService.process(atm);
    }
}
