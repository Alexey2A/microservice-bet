package com.example.parserfootball.service;

import com.example.parserfootball.dto.GameDto;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Parser {

    List<GameDto> getGames(String url) throws InterruptedException;

    GameDto parseGame(WebElement element);

}
