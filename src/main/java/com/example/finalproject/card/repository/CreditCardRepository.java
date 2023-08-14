package com.example.finalproject.card.repository;

import com.example.finalproject.card.core.exception.creditCardExceptions.NotFoundCreditCardException;
import com.example.finalproject.card.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findByCardNumber(String cardNumber);
    CreditCard findById(long creditCardId) throws NotFoundCreditCardException;
    CreditCard findByCustomer_IdentityNumber(String identityNumber) throws NotFoundCreditCardException;
    CreditCard findByIban(String iban);
}
