package com.example.parserfootball.service;

import com.example.parserfootball.dto.GameDto;
import com.example.parserfootball.utils.NamesGames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParserFootballLigastavok implements Parser {
    private static List<GameDto> games = new ArrayList<>();
    private static List<WebElement> nameGames = new ArrayList<>();
    private final static String WEBSITE = "Ligastavok";

    private final String teamsProperties;

    public ParserFootballLigastavok(@Value("${teams.properties}") String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }

    @Override
    public List<GameDto> getGames (String url) throws InterruptedException {
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
    public GameDto parseGame(WebElement element) {
        nameGames = element.findElements(By.className("bui-commands__command-d517c1"));                 //TODO исправить!!!
        String team1 = NamesGames.comparisonOfTeamNames(nameGames.get(0).getText(), teamsProperties);
        String team2 = NamesGames.comparisonOfTeamNames(nameGames.get(1).getText(), teamsProperties);

        List<WebElement> elements = element.findElements(By.className("bui-outcome-87025c"));

        Map<String, Double> coef = new HashMap<>();

        for (int i = 0; i < GameDto.RESULTS.length; i++) {
            String info = GameDto.RESULTS[i];
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
        return new GameDto(null, null, null, coef, WEBSITE);
    }
}
