package com.example.finalproject.account.core.request;

import com.example.finalproject.account.entity.enums.Maturity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMoneySavingAccount {
    private long savingAccountId;
    private Maturity maturity;
    private BigDecimal amount;
}
