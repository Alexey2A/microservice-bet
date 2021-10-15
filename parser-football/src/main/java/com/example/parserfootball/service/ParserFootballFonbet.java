package com.example.parserfootball.service;

import com.example.parserfootball.dto.Game;
import com.example.parserfootball.dto.NamesGames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ParserFootballFonbet implements Parser {
    private final static String WEBSITE = "Fonbet";

    private static List<Game> games = new ArrayList<>();
    private static List<WebElement> nameGames = new ArrayList<>();
    private static String[] names = null;

    @Override
    public List<Game> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(3000);

        nameGames = driver.findElements(By.xpath("//div[@class='table__match-title-text']"));

        List<WebElement> tableBtn = driver.findElements(By.className("table__btn"));

        WebElement parentElement = null;

        for (int i = 0; i < tableBtn.size(); i++) {
            names = Arrays.stream(nameGames.get(i).getText().split(" — ")).map(String::trim).toArray(String[]::new);
            names[0]= NamesGames.comparisonOfTeamNames(names[0], "teams.properties");
            names[1]=NamesGames.comparisonOfTeamNames(names[1], "teams.properties");
            parentElement = tableBtn.get(i).findElement(By.xpath("./../.."));
            games.add(parseGame(parentElement));
        }
        driver.quit();
        return games;
    }

    @Override
    public Game parseGame(WebElement element) {

        List<WebElement> elements = element.findElements(By.className("table__col"));

        Map<String, Double> coef = new HashMap<>();

            for (int i = 0; i < Game.RESULTS.length; i++) {
                String info = Game.RESULTS[i];
                String count = elements.get(i+2).getText();

                try {
                    coef.put(info, Double.parseDouble(count));
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }

        return new Game(names, null, coef, WEBSITE);
    }
}
