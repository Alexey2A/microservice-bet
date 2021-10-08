package org.example.better.service;

import org.example.better.dto.Game;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ParserFootballFonbet implements Parser {
    private final static String WEBSITE = "Fonbet";

    private static List<Game> games = new ArrayList<>();

    @Override
    public List<Game> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(3000);

        List<WebElement> tableBtn = driver.findElements(By.className("sport-base-event--dByYH"));

        for (int i = 0; i < tableBtn.size(); i++) {
            games.add(parseGame(tableBtn.get(i)));
        }
        driver.quit();
        return games;
    }

    @Override
    public Game parseGame(WebElement element) {
        String[] names = Arrays.stream(element.findElement(By.className("table-component-text--2U5hR")).getText().split("—")).map(String::trim).toArray(String[]::new);

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
