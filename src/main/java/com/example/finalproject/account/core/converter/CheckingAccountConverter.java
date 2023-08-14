package com.example.finalproject.account.core.converter;


import com.example.finalproject.account.core.response.CheckingAccountDTO;
import com.example.finalproject.account.entity.CheckingAccount;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
public interface CheckingAccountConverter {
    CheckingAccountDTO toDTO(CheckingAccount checkingAccount);
}
