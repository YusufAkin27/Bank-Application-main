package com.example.finalproject.card.core.converter;

import com.example.finalproject.card.core.response.CreditCardDTO;
import com.example.finalproject.card.entity.CreditCard;

public interface CreditCardConverter {
    CreditCardDTO toDTO(CreditCard creditCard);
}
