package com.example.finalproject.common.repository;


import com.example.finalproject.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNumber(String identity);
}
