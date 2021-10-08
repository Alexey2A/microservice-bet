package com.example.parserhockey.service;

import com.example.parserhockey.dto.GameHockey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service

public class ForkServiceHockey {
    private final static String URL_HOCKEY_Winline = "https://winline.ru/stavki/sport/xokkej/rossiya/";
    private final static String URL_HOCKEY_Olimpbet = "https://www.olimp.bet/line/2/1811822";

    private final ParserHockeyWinline parserHockeyWinline;
    private final ParseHockeyOlimpbet parseHockeyOlimpbet;

    @Autowired
    public ForkServiceHockey(ParserHockeyWinline parserHockeyWinline, ParseHockeyOlimpbet parseHockeyOlimpbet) {
        this.parserHockeyWinline = parserHockeyWinline;
        this.parseHockeyOlimpbet = parseHockeyOlimpbet;
    }
    public List<String> parserStart() throws InterruptedException {

        List<List<GameHockey>> games = new ArrayList<>();

        System.out.println("WINLINE");
        List<GameHockey> gamesHockeyWinline = parserHockeyWinline.getGames(URL_HOCKEY_Winline);
        games.add(gamesHockeyWinline);
        for (GameHockey g : gamesHockeyWinline) {
            System.out.println(g);
        }
        System.out.println(gamesHockeyWinline.size());
        System.out.println("----------");

        System.out.println("OLIMPBET");
        List<GameHockey> gamesHockeyOlimpbet = parseHockeyOlimpbet.getGames(URL_HOCKEY_Olimpbet);
        games.add(gamesHockeyOlimpbet);
        for (GameHockey g : gamesHockeyOlimpbet) {
            System.out.println(g);
        }
        System.out.println(gamesHockeyOlimpbet.size());
        System.out.println("----------");

        List<String> listOfForkChecksForTwoMatches = new ArrayList<>();

        for (List<GameHockey> g: sameGamesList(gamesHockeyWinline, gamesHockeyOlimpbet)) {
            System.out.println(g);
            for (GameHockey game : g) {
                double[] coefficients = {game.getCoef().get("П1"), game.getCoef().get("П2"), game.getCoef().get("Х")};
                for (int i = 0; i < coefficients.length; i++) {
                    for (GameHockey game2 : g) {
                        if (game != game2) {
                            double[] coefficients2 = {game2.getCoef().get("П1"), game2.getCoef().get("П2"), game2.getCoef().get("Х")};
                            double result = 1 / coefficients[i];
                            for (int j = 0; j < coefficients2.length; j++) {
                                if (j != i) {
                                    result += 1/coefficients2[j];
                                }
                            }
                            String s = new String("Проверка вилки: " + String.valueOf(result) + " " +
                                    game + " " + coefficients[i] + " " + game2 + " " + i + "\n");
                            listOfForkChecksForTwoMatches.add(s);
                            //System.out.printf("Проверка вилки: %f %s %s %d %n", result, game, game2, i);
                        }
                    }
                }
            }
        }
        return listOfForkChecksForTwoMatches;
    }
    public static List<List<GameHockey>> sameGamesList(List<GameHockey>... gamesLists){
        if (gamesLists == null || gamesLists.length == 0)
            return Collections.emptyList();
        List<List<GameHockey>> gamesFinalList = new ArrayList<>();

        for (List<GameHockey> gameList: gamesLists) {
            gameLoop:
            for (GameHockey game: gameList) {
                for (List<GameHockey> sameGames: gamesFinalList) {
                    if (sameGames.contains(game))
                        continue gameLoop;
                }
                List<GameHockey> sameGames = new ArrayList<>();
                sameGames.add(game);

                gamesFinalList.add(sameGames);

                for (List<GameHockey> gameList2: gamesLists) {
                    if (gameList != gameList2) {
                        for (GameHockey game2: gameList2) {
                            if (areGamesTheSame(game, game2))
                                sameGames.add(game2);
                        }
                    }
                }
            }
        }
        return gamesFinalList;
    }

    private static boolean areGamesTheSame(GameHockey game, GameHockey game2) {

        String[] game1Names = game.getNames();
        String[] game2Names = game2.getNames();

        return  (game1Names[0].equals(game2Names[0]) && game1Names[1].equals(game2Names[1])) ||
                (game1Names[0].equals(game2Names[1]) && game1Names[1].equals(game2Names[0]));

    }
}
