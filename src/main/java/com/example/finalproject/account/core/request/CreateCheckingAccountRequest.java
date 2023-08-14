package com.example.finalproject.account.core.request;

import com.example.finalproject.account.entity.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCheckingAccountRequest {
    private String bankCode;
    private String branchCode;
    private String branchName;
    private String ibanNo;
    private String accountNo;
    private String AccountName;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal lockedBalance = BigDecimal.ZERO;
    private boolean isActive = true;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private LocalDate createdAt;
}
