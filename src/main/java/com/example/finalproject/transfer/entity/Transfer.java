package com.example.finalproject.transfer.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String myIban;
    private String iban;
    private String nameAndSurname;
    private BigDecimal amount;
    private String description;



}
