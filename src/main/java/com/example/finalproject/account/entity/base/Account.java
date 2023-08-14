package com.example.finalproject.account.entity.base;

import com.example.finalproject.account.entity.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String iban;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal lockedBalance = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private LocalDate createdAt;
    private boolean isActive = true;

}
