package com.example.bet.common.repository;

import com.example.bet.common.entity.Fork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForkRepository extends JpaRepository<Fork, Long> {
    Optional<Fork> findByMargin(Double margin);
}
