package com.example.finalproject.account.entity;

import com.example.finalproject.account.entity.base.Account;
import com.example.finalproject.card.entity.DebitCard;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class CheckingAccount extends Account {

    private String bankCode;

    private String branchCode;

    private String branchName;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    private DebitCard debitCard;

    @JsonIgnoreProperties("accountActivities")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> accountActivities ;

    public void addCheckingAccountActivity(Activity activity) {
        if (accountActivities == null) {
            accountActivities = new ArrayList<>();
        }

        if (activity != null) {
            accountActivities.add(activity);
            accountActivities.sort(Comparator.comparing(Activity::getProcessDate).reversed());
        }
    }
}
