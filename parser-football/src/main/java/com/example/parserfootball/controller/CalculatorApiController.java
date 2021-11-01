package com.example.parserfootball.controller;

import com.example.parserfootball.dto.CalculatorDto;
import com.example.parserfootball.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorApiController {
    private final CalculatorService calculatorService;

    public CalculatorApiController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping
    public ResponseEntity<Integer> calculator(@RequestBody CalculatorDto calculatorDto){

        return ResponseEntity.ok(calculatorService.sum(calculatorDto.getNumber1(),calculatorDto.getNumber2()));
    }
}
