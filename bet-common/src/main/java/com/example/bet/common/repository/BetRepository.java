package com.example.bet.common.repository;

import com.example.bet.common.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {
}
