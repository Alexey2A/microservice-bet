package com.example.parserfootball.service;

import com.example.parserfootball.dto.Game;
import com.example.parserfootball.dto.NamesGames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParserFootballLigastavok implements Parser {
    private static List<Game> games = new ArrayList<>();
    private static List<WebElement> nameGames = new ArrayList<>();
    private final static String WEBSITE = "Ligastavok";

    @Override
    public List<Game> getGames (String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();

        WebElement tableEvents = driver.findElement(By.className("events-proposed__wrapper-events-f8fbd6"));
        //tableEvents.findElement(By.className("bui-events-lazy-bar__button-a683cb")).click();
        Thread.sleep(1000);

        List<WebElement> elements = tableEvents.findElements(By.className("bui-event-row-dfbc70"));

        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            games.add(parseGame(element));
        }
        driver.quit();



        return games;
    }

    @Override
    public Game parseGame(WebElement element) {
        nameGames = element.findElements(By.className("bui-commands__command-d517c1"));
        String team1 = NamesGames.comparisonOfTeamNames(nameGames.get(0).getText(), "teams.properties");
        String team2 = NamesGames.comparisonOfTeamNames(nameGames.get(1).getText(), "teams.properties");

        List<WebElement> elements = element.findElements(By.className("bui-outcome-87025c"));

        Map<String, Double> coef = new HashMap<>();

        for (int i = 0; i < Game.RESULTS.length; i++) {
            String info = Game.RESULTS[i];
            String count = new String();
            if (elements.size() >= 3 && elements.get(i).getText().contains(",")) {
                count = new String(elements.get(i).getText().replace(',' , '.' ));
            } else count = "1.0";

            try {
                coef.put(info, Double.parseDouble(count));
            } catch (NumberFormatException exception) {
                coef.put(info, 1.0);
            }
        }
        return new Game(new String[]{team1, team2}, null, coef, WEBSITE);
    }
}
