package com.example.finalproject.account.repository;


import com.example.finalproject.account.entity.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount,Long> {
}
