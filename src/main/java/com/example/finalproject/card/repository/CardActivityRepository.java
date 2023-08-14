package com.example.finalproject.card.repository;

import com.example.finalproject.card.entity.base.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardActivityRepository extends JpaRepository<Activity, Long> {
}
