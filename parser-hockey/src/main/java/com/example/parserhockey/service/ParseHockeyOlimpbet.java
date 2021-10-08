package com.example.parserhockey.service;

import com.example.parserhockey.dto.GameHockey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParseHockeyOlimpbet implements ParserHockey {
    private final static String WEBSITE = "Olimpbet";

    @Override
    public List<GameHockey> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        List<GameHockey> games = new ArrayList<>();

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        Thread.sleep(3000);
        driver.manage().window().maximize();

        List<WebElement> elements = driver.findElements(By.className("common__Item-sc-1p0w8dw-0"));

        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            games.add(parseGame(element));
        }
        driver.quit();

        return games;
    }

    @Override
    public GameHockey parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("default__Link-sc-14zuwl2-0")).getText();
        String[] names = Arrays.stream(nameGame.split(" - ")).map(String::trim).toArray(String[]::new);



        List<WebElement> elements = element.findElements(By.className("common-button__CommonButton-sc-xn93w0-0"));

        Map<String, Double> coef = new HashMap<>();
        if (elements.size() < GameHockey.RESULTS.length) {
            for (int i = 0; i < GameHockey.RESULTS.length; i++) {
                String info = GameHockey.RESULTS[i];
                try {
                    coef.put(info, 1.0);
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }
        } else {
            for (int i = 0; i < GameHockey.RESULTS.length; i++) {
                String info = GameHockey.RESULTS[i];
                String count = elements.get(i).getText();
                try {
                    coef.put(info, Double.parseDouble(count));
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }
           /* String totalMore = String.format("%s%f", "Тотал больше", Double.parseDouble(elements.get(10).getText()));
            double totalMoreCoef = Double.parseDouble(elements.get(11).getText());
            String totalLess = String.format("%s%f", "Тотал меньше", Double.parseDouble(elements.get(10).getText()));
            double totalLessCoef = Double.parseDouble(elements.get(12).getText());
            coef.put(totalMore, totalMoreCoef);
            coef.put(totalLess, totalLessCoef);*/
        }
        
        return new GameHockey(names, null, coef, WEBSITE);
    }
}
