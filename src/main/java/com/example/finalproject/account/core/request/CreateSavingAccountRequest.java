package com.example.finalproject.account.core.request;

import com.example.finalproject.account.entity.enums.AccountType;
import com.example.finalproject.account.entity.enums.PurposeSaving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSavingAccountRequest {
    @Enumerated(EnumType.STRING)
    private PurposeSaving purposeSaving;
    private boolean isActive = true;
    @Temporal(TemporalType.DATE)
    private Date maturityDate;
    private String ibanNo;
    private String accountNo;
    private String AccountName;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal lockedBalance = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private LocalDate createdAt;
    private BigDecimal successRate = BigDecimal.ZERO;
    private BigDecimal targetAmount = BigDecimal.ZERO;

}
