package com.example.finalproject.card.entity;

import com.example.finalproject.card.entity.base.Card;
import com.example.finalproject.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreditCard extends Card {
    private BigDecimal debt;
    private BigDecimal monthlyDebt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
}
