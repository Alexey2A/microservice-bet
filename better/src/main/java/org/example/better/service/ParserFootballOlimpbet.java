package org.example.better.service;

import org.example.better.dto.Game;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParserFootballOlimpbet implements Parser {
    private static List<Game> games = new ArrayList<>();
    private static List<WebElement> nameGames = new ArrayList<>();
    private final static String WEBSITE = "Olimpbet";

    @Override
    public List<Game> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(3000);

        List<WebElement> elements = driver.findElements(By.className("common__Item-sc-1p0w8dw-0"));

        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            games.add(parseGame(element));
        }
        driver.quit();

        return games;
    }

    @Override
    public Game parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("default__Link-sc-14zuwl2-0")).getText();
        String[] names = Arrays.stream(nameGame.split("-")).map(String::trim).toArray(String[]::new);

        Map<String, Double> coef = new HashMap<>();

        if (element.findElements(By.className("common-button__CommonButton-sc-xn93w0-0")).isEmpty()) {
            for (int i = 0; i < Game.RESULTS.length; i++) {
                String info = Game.RESULTS[i];
                try {
                    coef.put(info, 1.0);
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }
        } else {

            List<WebElement> elements = element.findElements(By.className("common-button__CommonButton-sc-xn93w0-0"));

            for (int i = 0; i < Game.RESULTS.length; i++) {
                String info = Game.RESULTS[i];
                String count = elements.get(i).getText();
                try {
                    coef.put(info, Double.parseDouble(count));
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }
        }
        return new Game(names, null, coef, WEBSITE);
    }
}
