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
public class ParserFootballFonbet implements Parser {
    private final static String WEBSITE = "Fonbet";

    private static List<Game> games = new ArrayList<>();
    private static List<WebElement> nameGames = new ArrayList<>();
    private static String[] names = null;

    private final String teamsProperties;

    public ParserFootballFonbet(@Value("${teams.properties}") String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }

    @Override
    public List<Game> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(3000);

        nameGames = driver.findElements(By.className("sport-base-event__main__caption--xKTdJ"));

        List<WebElement> elements = driver.findElements(By.className("sport-base-event--dByYH"));

        for (int i = 0; i < elements.size(); i++) {
            names = Arrays.stream(nameGames.get(i).getText().split(" — ")).map(String::trim).toArray(String[]::new);
            names[0]= NamesGames.comparisonOfTeamNames(names[0], teamsProperties);
            names[1]=NamesGames.comparisonOfTeamNames(names[1], teamsProperties);
            games.add(parseGame(elements.get(i)));
        }
        driver.quit();
        return games;
    }

    @Override
    public Game parseGame(WebElement element) {

        List<WebElement> elements = element.findElements(By.className("table-component-factor-value_single--3htyA"));

        Map<String, Double> coef = new HashMap<>();

            for (int i = 0; i < Game.RESULTS.length; i++) {
                String info = Game.RESULTS[i];
                String count = elements.get(i).getText();

                try {
                    coef.put(info, Double.parseDouble(count));
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }

        return new Game(names, null, coef, WEBSITE);
    }
}
