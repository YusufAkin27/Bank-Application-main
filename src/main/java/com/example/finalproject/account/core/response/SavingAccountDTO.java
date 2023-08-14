package com.example.finalproject.account.core.response;

import com.example.finalproject.account.entity.enums.Maturity;
import com.example.finalproject.card.entity.base.Activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountDTO {
    private long id;
    private String ibanNo;
    private String accountNo;
    private String AccountName;
    private BigDecimal balance;
    private BigDecimal lockedBalance;
    private LocalDate createdAt;
    private BigDecimal successRate;
    private BigDecimal targetAmount;
    @Enumerated(value = EnumType.ORDINAL)
    private Maturity maturity;
    @Temporal(TemporalType.DATE)
    private LocalDate maturityDate;

    private List<Activity> accountActivities = new ArrayList<>();
}
