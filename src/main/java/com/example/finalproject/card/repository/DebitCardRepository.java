package com.example.finalproject.card.repository;

import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.card.entity.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
    DebitCard findByIban(String iban);
    DebitCard findById(long id) throws NotFoundDebitCardException;
    DebitCard findByCardNumber(String cardNumber);
}
