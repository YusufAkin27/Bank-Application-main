package com.example.finalproject.card.core.response;

import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.customer.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDTO {
    private Long id;
    private String cardNumber;
    private String cvv;
    private String password;
    @Temporal(value = TemporalType.DATE)
    private LocalDate expiryDate;
    private String nameAndSurname;
    private String iban;
    private BigDecimal balance;
    private boolean isActive ;
    private BigDecimal debt;
    private BigDecimal monthlyDebt;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> activityList = new ArrayList<>();
}
