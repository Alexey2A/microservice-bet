package com.example.parserfootball;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.example.bet.common.repository")
@EntityScan("com.example.bet.common.entity")
public class BetConfiguration {
}
