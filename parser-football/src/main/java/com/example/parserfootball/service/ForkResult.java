package com.example.parserfootball.service;

import com.example.parserfootball.dto.Game;

import java.util.List;

public class ForkResult {
    private double result;
    private List<Game> games;

    public double getResult() {
        return result;
    }

    public List<Game> getGames() {
        return games;
    }
}
