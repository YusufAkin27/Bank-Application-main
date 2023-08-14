package com.example.finalproject.customer.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.example.finalproject.account.entity.CheckingAccount;
import com.example.finalproject.account.entity.SavingAccount;

import com.example.finalproject.card.entity.CreditCard;


import com.example.finalproject.common.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends User {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private long id;
    private String identityNumber;
    private String name;
    private String surname;
    private String telephone;
    private BigDecimal income;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthDay;
    private LocalDate createdAt;
    private boolean isActive;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private Address address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CheckingAccount> checkingAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<CreditCard>creditCards=new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<SavingAccount> savingAccounts = new ArrayList<>();



}

