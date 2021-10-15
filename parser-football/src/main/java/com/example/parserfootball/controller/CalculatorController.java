package com.example.parserfootball.controller;

import com.example.parserfootball.service.CalculatorService;
import com.example.parserfootball.service.ForkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;

@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;
    private final ForkService forkService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService, ForkService forkService) {
        this.calculatorService = calculatorService;
        this.forkService = forkService;
    }

    @GetMapping("/")
    public String index(ModelMap moodel) throws InterruptedException, ExecutionException {

        moodel.put("games", forkService.parserStart());
        return "index";
    }

    @PostMapping("/calculator")
    public String calculator(@RequestParam int number1, @RequestParam int number2, ModelMap model){
        int sum = calculatorService.sum(number1, number2);

        model.put("res", sum);
        return "index";
    }
}
