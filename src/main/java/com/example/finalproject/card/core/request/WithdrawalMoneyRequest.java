package com.example.finalproject.card.core.request;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawalMoneyRequest {
    private Long creditCardId;
    private String password;
    private BigDecimal amount;
    private int month;
}
