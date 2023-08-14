package com.example.finalproject.atm.repository;

import com.example.finalproject.atm.entity.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm,Long> {

}
