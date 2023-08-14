package com.example.finalproject.card.entity.base;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cardNumber;
    private String nameAndSurname;
    private String iban;
    private BigDecimal balance;
    private String password;
    private String cvv;
    private LocalDate expiryDate;
    private boolean isActive = true;

    @JsonIgnoreProperties("card")
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activityList = new ArrayList<>();

    public void addCardActivity(Activity activity) {
        activityList.add(activity);
        activityList.sort(Comparator.comparing(Activity::getProcessDate).reversed());
    }
}
