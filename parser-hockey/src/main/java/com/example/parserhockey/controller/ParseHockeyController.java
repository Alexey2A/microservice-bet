package com.example.parserhockey.controller;

import com.example.parserhockey.entity.DbHockey;
import com.example.parserhockey.repository.ForkHockeyRepository;
import com.example.parserhockey.service.ForkServiceHockey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ParseHockeyController {
    private final ForkServiceHockey forkServiceHockey;
    private final ForkHockeyRepository forkHockeyRepository;

    @Autowired
    public ParseHockeyController(ForkServiceHockey forkServiceHockey, ForkHockeyRepository forkHockeyRepository) {
        this.forkServiceHockey = forkServiceHockey;
        this.forkHockeyRepository = forkHockeyRepository;
    }

    @GetMapping("/")
    public String parseHockey(ModelMap model) throws InterruptedException {

        List<String> forkHockeylist = forkServiceHockey.parserStart();
        forkHockeyRepository.deleteAll();
        for (String s : forkHockeylist) {
            forkHockeyRepository.save(new DbHockey(s));

        }
        // model.put("games", forkServiceHockey.parserStart());
        return "parser-hockey";
    }
}

