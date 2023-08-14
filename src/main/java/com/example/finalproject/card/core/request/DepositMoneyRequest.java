package com.example.finalproject.card.core.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoneyRequest {
    private Long creditCardId;
    private String password;
    private BigDecimal amount;

}
