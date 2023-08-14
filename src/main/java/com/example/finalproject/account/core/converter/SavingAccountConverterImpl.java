package com.example.finalproject.account.core.converter;


import com.example.finalproject.account.entity.SavingAccount;
import com.example.finalproject.account.core.response.SavingAccountDTO;

import org.springframework.stereotype.Component;

@Component
public class SavingAccountConverterImpl implements SavingAccountConverter {

    @Override
    public SavingAccountDTO toDTO(SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        savingAccountDTO.setId(savingAccount.getId());
        savingAccountDTO.setIbanNo(savingAccount.getIban());
        savingAccountDTO.setAccountNo(savingAccount.getAccountNumber());
        savingAccountDTO.setAccountName(savingAccount.getAccountHolderName());
        savingAccountDTO.setBalance(savingAccount.getBalance());
        savingAccountDTO.setLockedBalance(savingAccount.getLockedBalance());
        savingAccountDTO.setCreatedAt(savingAccount.getCreatedAt());
        savingAccountDTO.setSuccessRate(savingAccount.getSuccessRate());
        savingAccountDTO.setTargetAmount(savingAccount.getTargetAmount());
        savingAccountDTO.setMaturity(savingAccount.getMaturity());
        savingAccountDTO.setMaturityDate(savingAccount.getMaturityDate());
        savingAccountDTO.setAccountActivities(savingAccount.getAccountActivities());
        return savingAccountDTO;
    }

}
