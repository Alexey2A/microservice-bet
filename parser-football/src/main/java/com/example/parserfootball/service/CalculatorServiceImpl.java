package com.example.parserfootball.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public int sum(int a, int b) {
        return a + b;
    }
}
