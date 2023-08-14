package com.example.finalproject.atm.entity;

import com.example.finalproject.atm.entity.enums.Process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cardNumber;
    private String password;
    private Process process;
    private BigDecimal amount;
    private LocalDate time;


}
