package com.example.finalproject.account.entity;

import com.example.finalproject.account.entity.base.Account;
import com.example.finalproject.account.entity.enums.Maturity;
import com.example.finalproject.account.entity.enums.PurposeSaving;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class SavingAccount extends Account {
    private BigDecimal successRate = BigDecimal.ZERO;
    private BigDecimal targetAmount = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private PurposeSaving purposeSaving;
    @Enumerated(EnumType.STRING)
    private Maturity maturity;
    private LocalDate maturityDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnoreProperties("accountActivities")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> accountActivities = new ArrayList<>();

    public void addAccountActivity(Activity activity) {
        if (accountActivities == null) {
            accountActivities = new ArrayList<>();
        }
        accountActivities.add(activity);
        accountActivities.sort(Comparator.comparing(Activity::getProcessDate).reversed());
    }
}
