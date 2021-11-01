package com.example.parserfootball.service;

import com.example.parserfootball.dto.Game;
import com.example.parserfootball.utils.NamesGames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParserFootballWinline implements Parser {
    private final static String WEBSITE = "Winline";

    private final String teamsProperties;

    public ParserFootballWinline(@Value("${teams.properties}") String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }


    @Override
    public List<Game> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        List<Game> games = new ArrayList<>();
        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.className("table__item"));

        for (int i = 0; i < elements.size(); i++) {
            games.add(parseGame(elements.get(i)));
        }
        driver.quit();
        return games;
    }

    @Override
    public Game parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("statistic__match")).getAttribute("title");
        String[] names = Arrays.stream(nameGame.split(" - ")).map(String::trim).toArray(String[]::new);
        names[0]=NamesGames.comparisonOfTeamNames(names[0], teamsProperties);
        names[1]=NamesGames.comparisonOfTeamNames(names[1], teamsProperties);

        String[] dateInfo = new String[3];
        dateInfo[0] = element.findElement(By.className("statistic__date")).getText();
        dateInfo[1] = element.findElement(By.className("statistic__time")).getText();

        String date = dateInfo[0] + " " + dateInfo[1];

        Map<String, Double> coef = new HashMap<>();
        List<WebElement> coefficients = new ArrayList<>();
        coefficients = element.findElements(By.className("coefficient__td"));
        for (int i = 0; i < Game.RESULTS.length; i++) {
            String info = Game.RESULTS[i];
            String count = coefficients.get(i).getText();
            try {
                coef.put(info, Double.parseDouble(count));
            } catch (NumberFormatException exception) {
                coef.put(info, 1.0);
            }
        }
        return new Game(names, null, coef, WEBSITE);
    }

    @Override
    public String toString() {

        return "ParserFootballWinline{}";
    }
}
