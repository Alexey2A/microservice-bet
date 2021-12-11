package com.example.parserfootball.controller;

import com.example.bet.common.entity.Game;
import com.example.bet.common.repository.GameRepository;
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
    private final GameRepository gameRepository;

    @Autowired
    public CalculatorController(CalculatorService calculatorService, ForkService forkService, GameRepository gameRepository) {
        this.calculatorService = calculatorService;

        this.forkService = forkService;

        this.gameRepository = gameRepository;
    }

    @GetMapping("/")
    public String index(ModelMap moodel) throws InterruptedException, ExecutionException {
        Game game = new Game();
        game.setName("tt");
        gameRepository.save(game);
        gameRepository.findAll();

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
