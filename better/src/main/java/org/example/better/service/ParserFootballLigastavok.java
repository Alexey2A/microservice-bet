package org.example.better.service;

import org.example.better.dto.Game;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParserFootballLigastavok implements Parser {
    private static List<Game> games = new ArrayList<>();
    private static List<WebElement> nameGames = new ArrayList<>();
    private final static String WEBSITE = "Ligastavok";

    @Override
    public List<Game> getGames (String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();

        Proxy proxy = new Proxy();
        proxy.setAutodetect(false);
        proxy.setHttpProxy("194.233.69.41:443");
        proxy.setSslProxy("194.233.69.41:443");
        proxy.setNoProxy("no_proxy-var");

        chromeOptions.setCapability("proxy", proxy);

        /*chromeOptions.addArguments("--disable-extensions", "--profile-directory=Default"
                , "--incognito", "--disable-plugins-discovery", "--start-maximized");*/

        ChromeDriver driver = new ChromeDriver(chromeOptions);

       // Cookie cookie = new Cookie("qrator_jsr","1633108095.152.J3DYQrmogXU1TGUt-971v6a48tgpncj0kromcdc1749rk3sqm-00");

        driver.get(url);

        //driver.manage().deleteAllCookies();

        //driver.manage().addCookie(cookie);

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
    public Game parseGame(WebElement element) {
        nameGames = element.findElements(By.className("bui-commands__command-d517c1"));
        String team1 = TeamNamesFootballRpl.comparisonOfTeamNames(nameGames.get(0).getText());
        String team2 = TeamNamesFootballRpl.comparisonOfTeamNames(nameGames.get(1).getText());

        List<WebElement> elements = element.findElements(By.className("bui-outcome-87025c"));

        Map<String, Double> coef = new HashMap<>();

        for (int i = 0; i < Game.RESULTS.length; i++) {
            String info = Game.RESULTS[i];
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
        return new Game(new String[]{team1, team2}, null, coef, WEBSITE);
    }
}
