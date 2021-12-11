package com.example.parserfootball.service;

import com.example.parserfootball.dto.GameDto;
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

    private static List<GameDto> games = new ArrayList<>();

    private final String teamsProperties;

    public ParserFootballFonbet(@Value("${teams.properties}") String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }

    @Override
    public List<GameDto> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(3000);

        List<WebElement> elements = driver.findElements(By.className("sport-base-event--dByYH"));

        for (int i = 0; i < elements.size(); i++) {
            games.add(parseGame(elements.get(i)));
        }
        driver.quit();
        return games;
    }

    @Override
    public GameDto parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("sport-base-event__main__caption--xKTdJ")).getText();
        String[] originalNames = Arrays.stream(nameGame.split(" — ")).map(String::trim).toArray(String[]::new);
        if (originalNames.length < 2) return new GameDto(WEBSITE);
        String[] commonNames = new String[2];
        commonNames[0] = NamesGames.comparisonOfTeamNames(originalNames[0], teamsProperties);
        commonNames[1] = NamesGames.comparisonOfTeamNames(originalNames[1], teamsProperties);

        List<WebElement> elements = element.findElements(By.className("table-component-factor-value_single--3htyA"));

        Map<String, Double> coef = new HashMap<>();

            for (int i = 0; i < GameDto.RESULTS.length; i++) {
                String info = GameDto.RESULTS[i];
                String count = elements.get(i).getText();

                try {
                    coef.put(info, Double.parseDouble(count));
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }

        return new GameDto(commonNames, originalNames, null, coef, WEBSITE);
    }
}
