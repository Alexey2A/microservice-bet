package com.example.bet.common.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;

    @Column
    private String name;

    @Column
    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Game() {
    }

    public Game(String name, String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
        this.name = name;
        this.dateTime = formatter.format(ZonedDateTime.now());;          // TODO потом исправить!!       ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Game{" +
                "game_id=" + gameId +
                ", name='" + name + '\'' +
                '}';
    }
}
