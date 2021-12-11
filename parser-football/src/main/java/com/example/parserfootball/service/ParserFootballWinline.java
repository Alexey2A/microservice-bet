package com.example.parserfootball.service;

import com.example.parserfootball.dto.GameDto;
import com.example.parserfootball.utils.NamesGames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ParserFootballWinline implements Parser {
    private final static String WEBSITE = "Winline";

    private final String teamsProperties;

    public ParserFootballWinline(@Value("${teams.properties}") String teamsProperties) {
        this.teamsProperties = teamsProperties;
    }


    @Override
    public List<GameDto> getGames(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        List<GameDto> games = new ArrayList<>();
        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.className("table__item"));

        for (int i = 0; i < elements.size(); i++) {
            games.add(parseGame(elements.get(i)));
        }
        driver.quit();
        return games;
    }

    @Override
    public GameDto parseGame(WebElement element) {
        String nameGame = element.findElement(By.className("statistic__match")).getAttribute("title");
        String[] originalNames = Arrays.stream(nameGame.split(" - ")).map(String::trim).toArray(String[]::new);
        String[] commonNames = new String[2];

        commonNames[0]=NamesGames.comparisonOfTeamNames(originalNames[0], teamsProperties);
        commonNames[1]=NamesGames.comparisonOfTeamNames(originalNames[1], teamsProperties);

        String[] dateInfo = new String[3];
        dateInfo[0] = element.findElement(By.className("statistic__date")).getText();
        dateInfo[1] = element.findElement(By.className("statistic__time")).getText();

        String date = getDateAndTime(element);

        Map<String, Double> coef = new HashMap<>();
        List<WebElement> coefficients = new ArrayList<>();
        coefficients = element.findElements(By.className("coefficient__td"));
        for (int i = 0; i < GameDto.RESULTS.length; i++) {
            String info = GameDto.RESULTS[i];
            String count = coefficients.get(i).getText();
            try {
                coef.put(info, Double.parseDouble(count));
            } catch (NumberFormatException exception) {
                coef.put(info, 1.0);
            }
        }
        return new GameDto(commonNames, originalNames, date, coef, WEBSITE);
    }

    private static String getDateAndTime (WebElement element) {
        int year = LocalDateTime.now().getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");

        boolean locator1 = element.findElements(By.className("statistic__time")).size() > 0;
        boolean locator2 = element.findElements(By.className("table-counter")).size() > 0;

        if ( !locator1 || locator2
                || element.findElement(By.className("statistic__date")).getText().contains("П")
                || element.findElement(By.className("statistic__date")).getText().contains("Т"))
            return "сейчас";
        else if(element.findElement(By.className("statistic__date")).getText().contains("Ч")) {

            String[] time = element.findElement(By.className("statistic__time")).getText().split(":");
            String minutes = time[0];
            return formatter.format(LocalDateTime.now().plusMinutes(Long.parseLong(minutes)).plusMinutes(1));
        }
        else if (element.findElement(By.className("statistic__date")).getText().contains("Завтра")) {
            String[] time = element.findElement(By.className("statistic__time")).getText().split(":");
            return formatter.format(LocalDateTime.of(year, LocalDateTime.now().getMonthValue()
                    ,LocalDateTime.now().plusDays(1).getDayOfMonth()
                    ,Integer.parseInt(time[0])
                    ,Integer.parseInt(time[1])));
        }
        else if (element.findElement(By.className("statistic__date")).getText().contains("Сегодня")) {
            String[] time = element.findElement(By.className("statistic__time")).getText().split(":");
            return formatter.format(LocalDateTime.of(year, LocalDateTime.now().getMonthValue()
                    ,LocalDateTime.now().getDayOfMonth()
                    ,Integer.parseInt(time[0])
                    ,Integer.parseInt(time[1])));
        }
        else {
            String[] date = element.findElement(By.className("statistic__date")).getText().split("\\.");
            String[] time = element.findElement(By.className("statistic__time")).getText().split(":");
            return formatter.format(LocalDateTime.of(year, Integer.parseInt(date[1])
                    ,Integer.parseInt(date[0])
                    ,Integer.parseInt(time[0])
                    ,Integer.parseInt(time[1])));
        }
    }

    @Override
    public String toString() {

        return "ParserFootballWinline{}";
    }
}
