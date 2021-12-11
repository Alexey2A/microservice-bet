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
public class ParserFootballOlimpbet implements Parser {
    private static List<GameDto> games = new ArrayList<>();
    private static List<WebElement> nameGames = new ArrayList<>();
    private final static String WEBSITE = "Olimpbet";

    private final String teamsProperties;

    public ParserFootballOlimpbet(@Value("${teams.properties}") String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }

    @Override
    public List<GameDto> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(1000);

        List<WebElement> elements = driver.findElements(By.className("common__Item-sc-1p0w8dw-0"));

        for (int i = 0; i < elements.size(); i++) {
            games.add(parseGame(elements.get(i)));
        }
        driver.quit();

        return games;
    }

    @Override
    public GameDto parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("default__Link-sc-14zuwl2-0")).getText();
        String[] originalNames = Arrays.stream(nameGame.split(" - ")).map(String::trim).toArray(String[]::new);
        String[] commonNames = new String[2];
        commonNames[0]= NamesGames.comparisonOfTeamNames(originalNames[0], teamsProperties);
        commonNames[1]= NamesGames.comparisonOfTeamNames(originalNames[1], teamsProperties);

        String taboo = "&nbsp;";
        String dateTime = element.findElement(By.className("styled__EventDate-sc-vrkr7n-4")).getText();

        for (char c : taboo.toCharArray()) {
            dateTime = dateTime.replace(c, ' ');
        }

        Map<String, Double> coef = new HashMap<>();

        if (element.findElements(By.className("common-button__CommonButton-sc-xn93w0-0")).isEmpty()) {
            for (int i = 0; i < GameDto.RESULTS.length; i++) {
                String info = GameDto.RESULTS[i];
                try {
                    coef.put(info, 1.0);
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }
        } else {

            List<WebElement> elements = element.findElements(By.className("common-button__CommonButton-sc-xn93w0-0"));

            for (int i = 0; i < GameDto.RESULTS.length; i++) {
                String info = GameDto.RESULTS[i];
                String count = elements.get(i).getText();
                try {
                    coef.put(info, Double.parseDouble(count));
                } catch (NumberFormatException exception) {
                    coef.put(info, 1.0);
                }
            }
        }
        return new GameDto(commonNames, originalNames, null, coef, WEBSITE);
    }
}
