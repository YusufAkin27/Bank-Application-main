package com.example.finalproject.transfer.controller;


import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.transfer.business.abstracts.TransferService;
import com.example.finalproject.response.ServiceResponse;
import com.example.finalproject.transfer.exception.*;
import com.example.finalproject.transfer.entity.Transfer;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/transfer")
@RequiredArgsConstructor
public class TransferController {


    private final TransferService transferService;

    @PostMapping("/sendMoney")
    public ServiceResponse sendMoney(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                     @RequestBody Transfer transfer) throws NotFoundDebitCardException,
            DebitCardNotActiveException, WrongNameAndSurnameException, InsufficientBalanceException, TransferAmountOutOfRangeException, TransferTimeNotAllowedException, CustomerNotFoundException, IbanDoesNotBelongToYouException, NotFoundIbanException {
        return transferService.sendMoney(userDetail.getUser().getId(),transfer);
    }

    @GetMapping("/getAll")
    public List<Transfer>getAllTransfers() {
    return transferService.getAllTransfers();
    }
}
