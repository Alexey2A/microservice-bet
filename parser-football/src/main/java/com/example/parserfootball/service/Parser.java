package com.example.parserfootball.service;

import com.example.parserfootball.dto.Game;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Parser {

    List<Game> getGames(String url) throws InterruptedException;

    Game parseGame(WebElement element);

}
