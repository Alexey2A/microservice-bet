package org.example.better.service;

import org.example.better.dto.Game;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Parser {

    List<Game> getGames(String url) throws InterruptedException;

    Game parseGame(WebElement element);

}
