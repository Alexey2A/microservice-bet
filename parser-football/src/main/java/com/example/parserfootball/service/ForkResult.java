package com.example.parserfootball.service;

import com.example.parserfootball.dto.GameDto;

import java.util.List;

public class ForkResult {
    private double result;
    private List<GameDto> games;

    public double getResult() {
        return result;
    }

    public List<GameDto> getGames() {
        return games;
    }
}
