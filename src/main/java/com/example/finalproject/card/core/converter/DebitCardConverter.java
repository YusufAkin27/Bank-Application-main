package com.example.finalproject.card.core.converter;


import com.example.finalproject.card.core.response.DebitCardDTO;
import com.example.finalproject.card.entity.DebitCard;

public interface DebitCardConverter {
    DebitCardDTO DebitCardDTO(DebitCard debitCard);

}
