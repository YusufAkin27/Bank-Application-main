package com.example.finalproject.account.core.converter;

import com.example.finalproject.account.core.response.CheckingAccountDTO;
import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.card.core.converter.DebitCardConverter;
import com.example.finalproject.card.core.response.DebitCardDTO;
import com.example.finalproject.card.entity.DebitCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class CheckingAccountConverterImpl implements CheckingAccountConverter {

    private final DebitCardConverter debitCardConverter;


    @Override
    public CheckingAccountDTO toDTO(CheckingAccount checkingAccount) {
        CheckingAccountDTO checkingAccountDTO = new CheckingAccountDTO();
        checkingAccountDTO.setId(checkingAccount.getId());
        checkingAccountDTO.setIbanNo(checkingAccount.getIban());
        checkingAccountDTO.setAccountNo(checkingAccount.getAccountNumber());
        checkingAccountDTO.setAccountName(checkingAccount.getAccountHolderName());
        checkingAccountDTO.setBalance(checkingAccount.getBalance());
        checkingAccountDTO.setLockedBalance(checkingAccount.getLockedBalance());
        checkingAccountDTO.setAccountType(checkingAccount.getAccountType());
        checkingAccountDTO.setCreatedAt(checkingAccount.getCreatedAt());
        checkingAccountDTO.setBranchName(checkingAccount.getBranchName());
        checkingAccountDTO.setActive(checkingAccount.isActive());
        DebitCard debitCard=checkingAccount.getDebitCard();
        checkingAccountDTO.setAccountActivities(checkingAccount.getAccountActivities());
        if (checkingAccount.getDebitCard() != null) {
            DebitCardDTO debitCardDTO = debitCardConverter.DebitCardDTO(debitCard);
            checkingAccountDTO.setDebitCard(debitCardDTO);
        }
        if (checkingAccount.getDebitCard() == null) {
            checkingAccountDTO.setDebitCard(null);
        }
        return checkingAccountDTO;
    }
}
