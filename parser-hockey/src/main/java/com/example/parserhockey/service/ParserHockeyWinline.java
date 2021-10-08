package com.example.parserhockey.service;

import com.example.parserhockey.dto.GameHockey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParserHockeyWinline implements ParserHockey {
    private final static String WEBSITE = "Winline";

    @Override
    public List<GameHockey> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        List<GameHockey> games = new ArrayList<>();

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        Thread.sleep(3000);

        List<WebElement> elements = driver.findElements(By.className("table__item"));

        for (WebElement element : elements) {

            games.add(parseGame(element));
        }
        driver.quit();

        return games;
    }

    @Override
    public GameHockey parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("statistic__match")).getAttribute("title");
        String[] names = Arrays.stream(nameGame.split(" - ")).map(String::trim).toArray(String[]::new);

        Map<String, Double> coef = new HashMap<>();
        List<WebElement> coefficients = new ArrayList<>();
        coefficients = element.findElements(By.className("coefficient__td"));
        for (int i = 0; i < GameHockey.RESULTS.length; i++) {
            String info = GameHockey.RESULTS[i];
            String count = coefficients.get(i).getText();
            try {
                coef.put(info, Double.parseDouble(count));
            } catch (NumberFormatException exception) {
                coef.put(info, 1.0);
            }
        }
        return new GameHockey(names, null, coef, WEBSITE);
    }
}
