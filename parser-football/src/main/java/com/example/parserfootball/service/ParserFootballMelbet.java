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
public class ParserFootballMelbet implements Parser{
    private static final String WEBSITE = "MelBet";
    private final String teamsProperties;

    public ParserFootballMelbet(@Value("${teams.properties}")String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }

    @Override
    public List<GameDto> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(3000);

        List<GameDto> games = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.className("kofsTableBody"));

        for(WebElement element : elements){
            games.add(parseGame(element));
        }
        driver.quit();
        return games;
    }

    @Override
    public GameDto parseGame(WebElement element) {
        List<WebElement> nameGames = new ArrayList<>();
        nameGames = element.findElements(By.className("team"));
        String team1 = nameGames.get(0).getText();
        String team2 = nameGames.get(1).getText();
        String[] commonNames = new String[2];
        commonNames[0]= NamesGames.comparisonOfTeamNames(team1, teamsProperties);
        commonNames[1]=NamesGames.comparisonOfTeamNames(team2, teamsProperties);

        List<WebElement> coefficients =element.findElements(By.className("kof"));

        Map<String, Double> coef = new HashMap<>();

        for(int i = 0; i < GameDto.RESULTS.length; i++){
            coef.put(GameDto.RESULTS[i], Double.parseDouble(coefficients.get(i).getText()));
        }

        return new GameDto(commonNames,new String[]{team1,team2}, null, coef, WEBSITE);
    }
}
