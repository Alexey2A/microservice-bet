package com.example.bet.common.repository;

import com.example.bet.common.entity.Bookmaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmakerRepository extends JpaRepository<Bookmaker, Long> {
    Optional<Bookmaker> findByName(String name);
}
