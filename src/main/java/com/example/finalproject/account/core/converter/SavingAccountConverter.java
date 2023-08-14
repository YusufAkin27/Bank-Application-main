package com.example.finalproject.account.core.converter;

import com.example.finalproject.account.entity.SavingAccount;
import com.example.finalproject.account.core.response.SavingAccountDTO;


public interface SavingAccountConverter {
    SavingAccountDTO toDTO(SavingAccount savingAccount);
}
