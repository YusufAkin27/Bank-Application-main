package com.example.finalproject.transfer.rules;

import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.entity.DebitCard;
import com.example.finalproject.card.entity.base.Card;
import com.example.finalproject.transfer.exception.InsufficientBalanceException;
import com.example.finalproject.transfer.exception.TransferAmountOutOfRangeException;
import com.example.finalproject.transfer.exception.TransferTimeNotAllowedException;
import com.example.finalproject.transfer.exception.WrongNameAndSurnameException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TransferRules {


    public boolean isActive(Card card) throws DebitCardNotActiveException {
        if (!card.isActive()) {
            throw new DebitCardNotActiveException();
        }
        return true;
    }

    public boolean expiryDate(Card card) throws DebitCardNotActiveException {
        LocalDate expiryDate = card.getExpiryDate();
        LocalDate currentDate = LocalDate.now();
        if (expiryDate.isBefore(currentDate)) {
            card.setActive(false);
            throw new DebitCardNotActiveException();
        }

        return true;
    }
    public boolean checkTransferAmount(BigDecimal amount) throws TransferAmountOutOfRangeException {
        BigDecimal minimumAmount = new BigDecimal("10"); // Minimum transfer tutarı 10 TL olsun
        BigDecimal maximumAmount = new BigDecimal("10000"); // Maksimum transfer tutarı 10.000 TL olsun

        if (amount.compareTo(minimumAmount) < 0 || amount.compareTo(maximumAmount) > 0) {
            throw new TransferAmountOutOfRangeException();
        }

        return true;
    }
    public boolean nameAndSurnameControl(Card card, String name) throws WrongNameAndSurnameException {
        if (!card.getNameAndSurname().toLowerCase().equals(name.toLowerCase())) {
            throw new WrongNameAndSurnameException();
        }
        return true;
    }
    public boolean checkTransferTime(LocalTime transferTime) throws TransferTimeNotAllowedException {
        LocalTime startAllowedTime = LocalTime.of(6, 0); // Transferlara izin verilen başlangıç saati (06:00)
        LocalTime endAllowedTime = LocalTime.of(23, 0); // Transferlara izin verilen bitiş saati (23:00)

        if (transferTime.isBefore(startAllowedTime) || transferTime.isAfter(endAllowedTime)) {
            throw new TransferTimeNotAllowedException();
        }

        return true;
    }

    public boolean balanceControl(BigDecimal balance, BigDecimal amount) throws InsufficientBalanceException {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
        return true;
    }


}
