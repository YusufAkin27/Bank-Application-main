package com.example.finalproject.account.repository;

import com.example.finalproject.account.entity.CheckingAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {

    @Query("SELECT ca FROM CheckingAccount ca WHERE ca.accountNumber LIKE %:info% " +
            "OR ca.accountHolderName LIKE %:info% " +
            "OR ca.bankCode LIKE %:info% " +
            "OR ca.branchCode LIKE %:info% " +
            "OR ca.branchName LIKE %:info%")
    List<CheckingAccount> searchAccount(@Param("info") String info);
}
