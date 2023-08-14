package com.example.finalproject.card.entity;

import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.card.entity.base.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DebitCard extends Card {
    private BigDecimal lockedBalance;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CheckingAccount checkingAccount;
}