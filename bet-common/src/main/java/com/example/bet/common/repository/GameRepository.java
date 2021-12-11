package com.example.bet.common.repository;

import com.example.bet.common.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByNameAndDateTime(String name, String dateTime);
}
