package org.example.better.service;

import org.example.better.dto.Game;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParserFootballWinline implements Parser {
    private final static String WEBSITE = "Winline";

    @Override
    public List<Game> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        List<Game> games = new ArrayList<>();
        ChromeOptions chromeOptions = new ChromeOptions();

        Proxy proxy = new Proxy();
        proxy.setAutodetect(false);
        proxy.setHttpProxy("87.197.151.153:8118");
        proxy.setSslProxy("87.197.151.153:8118");
        //proxy.setNoProxy("no_proxy-var");

        chromeOptions.setCapability("proxy", proxy);

        ChromeDriver driver = new ChromeDriver(chromeOptions);


        driver.get(url);
        Thread.sleep(3000);
        List<WebElement> elements = driver.findElements(By.className("table__item"));

        elements.forEach(e -> games.add(parseGame(e)));
        /*for (WebElement element : elements) {
            games.add(parseGame(element));
        }*/
        driver.quit();
        return games;
    }

    @Override
    public Game parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("statistic__match")).getAttribute("title");
        String[] names = Arrays.stream(nameGame.split("-")).map(String::trim).toArray(String[]::new);

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
