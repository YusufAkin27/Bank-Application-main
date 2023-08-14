package com.example.finalproject.card.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String crossAccount;
    private BigDecimal amount;
    private String description;
    private Instant processDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
}
