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
public class ParserFootballPariMatch implements Parser {
    private static final String WEBSITE = "PariMatch";
    private final String teamsProperties;

    public ParserFootballPariMatch(@Value("${teams.properties}") String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }

    @Override
    public List<GameDto> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(5000);

        List<GameDto> games = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.className("_2c98cYcZ15eCL3kXBibIh_"));

        for(WebElement element : elements){
            games.add(
                    parseGame(element));
        }
        driver.quit();
        return games;
    }

    @Override
    public GameDto parseGame(WebElement element) {
        List<WebElement> nameGames = new ArrayList<>();
        nameGames = element.findElements(By.className("styles_name__2QIKf"));
        String[] originalNames = new String[2];
        if (nameGames.size()>1) {
            originalNames[0] = nameGames.get(0).getText();
            originalNames[1] = nameGames.get(1).getText();
        } else {
            return new GameDto(WEBSITE);
        }

        String[] commonNames = new String[2];
        commonNames[0] = NamesGames.comparisonOfTeamNames(nameGames.get(0).getText(), teamsProperties);
        commonNames[1] = NamesGames.comparisonOfTeamNames(nameGames.get(1).getText(), teamsProperties);

        String dateTime = element.findElement(By.className("styles_status__3CdNn")).getText();

        List<WebElement> coefficients =element.findElements(By.className("styles_value__1V_3B"));

        Map<String, Double> coef = new HashMap<>();

        for(int i = 0; i < GameDto.RESULTS.length; i++){
            coef.put(GameDto.RESULTS[i], Double.parseDouble(coefficients.get(i).getText()));
        }

        return new GameDto(commonNames, originalNames, null, coef, WEBSITE);
    }
}
