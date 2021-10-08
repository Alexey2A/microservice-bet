package com.example.parserhockey.service;

import com.example.parserhockey.dto.GameHockey;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface ParserHockey {

    List<GameHockey> getGames(String url) throws InterruptedException;

    GameHockey parseGame(WebElement element);
}
