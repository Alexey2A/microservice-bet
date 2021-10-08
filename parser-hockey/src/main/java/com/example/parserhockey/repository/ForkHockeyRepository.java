package com.example.parserhockey.repository;

import com.example.parserhockey.entity.DbHockey;
import org.springframework.data.repository.CrudRepository;

public interface ForkHockeyRepository extends CrudRepository<DbHockey, Long> {
}
