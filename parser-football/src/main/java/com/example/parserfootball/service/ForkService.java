package com.example.parserfootball.service;

import com.example.parserfootball.dto.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ForkService {
    private final static String URL_FOOTBALL_Winline = "https://winline.ru/stavki/sport/futbol/rossiya/rpl/";
    private final static String URL_FOOTBALL_Fonbet = "https://www.fonbet.ru/bets/football/11935";
    private final static String URL_FOOTBALL_Olimpbet = "https://www.olimp.bet/line/1/13802";
    private final static String URL_FOOTBALL_LigaStavok = "https://www.ligastavok.ru/bets/my-line/soccer/rossiia-id-350/rossiiskaia-premer-liga-id-5271";
    private final static String URL_FOOTBALL_PariMatch = "https://www.parimatch.ru/ru/football/premier-league-7ea2177607bc434a9209b6ff63eb9a90/prematch";

    private final ParserFootballLigastavok parserFootballLigastavok;
    private final ParserFootballOlimpbet parserFootballOlimpbet;
    private final ParserFootballWinline parserFootballWinline;
    private final ParserFootballPariMatch parserFootballPariMatch;

    @Autowired
    public ForkService(ParserFootballLigastavok parserFootballLigastavok, ParserFootballOlimpbet parserFootballOlimpbet,
                       ParserFootballWinline parserFootballWinline, ParserFootballPariMatch parserFootballPariMatch) {
        this.parserFootballLigastavok = parserFootballLigastavok;
        this.parserFootballOlimpbet = parserFootballOlimpbet;
        this.parserFootballWinline = parserFootballWinline;
        this.parserFootballPariMatch = parserFootballPariMatch;
    }

    public List<String> parserStart() throws InterruptedException, ExecutionException {

        List<List<Game>> games = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Future<List<Game>> foolballWinlineResult = executorService.submit(() -> {
            System.out.println("WINLINE");
            List<Game> gamesFootballWinline = parserFootballWinline.getGames(URL_FOOTBALL_Winline);
            /*games.add(gamesFootballWinline);
            for (Game g : gamesFootballWinline) {
                System.out.println(g);
            }
            System.out.println(gamesFootballWinline.size());
            System.out.println("----------");*/
            return gamesFootballWinline;
        });


       /* System.out.println("FONBET");
        ParserFootballFonbet parserFootballFonbet = new ParserFootballFonbet();
        List<Game> gamesFootballFonbet = parserFootballFonbet.getGames(URL_FOOTBALL_Fonbet);
        games.add(gamesFootballFonbet);
        for (Game g : gamesFootballFonbet) {
            System.out.println(g);
        }
        System.out.println(gamesFootballFonbet.size());
        System.out.println("----------");*/

        Future<List<Game>> foolballOlimpbetResult = executorService.submit(() -> {
            System.out.println("OLIMPBET");
            List<Game> gamesFootballOlimpbet = parserFootballOlimpbet.getGames(URL_FOOTBALL_Olimpbet);
            /*games.add(gamesFootballOlimpbet);
            for (Game g : gamesFootballOlimpbet) {
                System.out.println(g);
            }
            System.out.println(gamesFootballOlimpbet.size());
            System.out.println("----------");*/
            return gamesFootballOlimpbet;
        });

        /*Future<List<Game>> foolballLigastavokResult = executorService.submit(() -> {
            System.out.println("LigaStavok");
            List<Game> gamesFootballLigastavok = parserFootballLigastavok.getGames(URL_FOOTBALL_LigaStavok);
            *//*games.add(gamesFootballLigastavok);
            for (Game g : gamesFootballLigastavok) {
                System.out.println(g);
            }
            System.out.println(gamesFootballLigastavok.size());
            System.out.println("----------");*//*
            return gamesFootballLigastavok;
        });*/

        Future<List<Game>> foolballPariMatchResult = executorService.submit(() -> {
            System.out.println("PariMatch");
            List<Game> gamesFootballPariMatch = parserFootballPariMatch.getGames(URL_FOOTBALL_PariMatch);
           /* games.add(gamesFootballPariMatch);
            for (Game g : gamesFootballPariMatch) {
                System.out.println(g);
            }
            System.out.println(gamesFootballPariMatch.size());
            System.out.println("----------");*/
            return gamesFootballPariMatch;
        });

        var gamesFootballWinline = foolballWinlineResult.get();
        var gamesFootballOlimpbet = foolballOlimpbetResult.get();
        //var gamesFootballLigastavok = foolballLigastavokResult.get();
        var gamesFootballPariMatch = foolballPariMatchResult.get();


        List<String> listOfForkChecksForTwoMatches = new ArrayList<>();

        for (List<Game> g : sameGamesList(gamesFootballOlimpbet, gamesFootballPariMatch, gamesFootballWinline)) {
            System.out.println(g);
            for (Game game : g) {
                double[] coefficients = {game.getCoef().get("П1"), game.getCoef().get("П2"), game.getCoef().get("Х")};
                for (int i = 0; i < coefficients.length; i++) {
                    for (Game game2 : g) {
                        if (game != game2) {
                            double[] coefficients2 = {game2.getCoef().get("П1"), game2.getCoef().get("П2"), game2.getCoef().get("Х")};
                            double result = 1 / coefficients[i];
                            double coefficient2 = 1;
                            for (int j = 0; j < coefficients2.length; j++) {
                                if (j != i) {
                                    result += 1 / coefficients2[j];
                                    coefficient2 = coefficients2[j];
                                }
                            }
                            String string = new String("Проверка вилки: " + String.valueOf(result) + " " +
                                    game + " " + coefficients[i] + " " + game2 + coefficient2 + " " + i + "\n");
                            listOfForkChecksForTwoMatches.add(string);
                            //System.out.printf("Проверка вилки: %f %s %s %d %n", result, game, game2, i);
                        }
                    }
                }
            }
        }
        return listOfForkChecksForTwoMatches;
    }


    public static List<List<Game>> sameGamesList(List<Game>... gamesLists) {
        if (gamesLists == null || gamesLists.length == 0)
            return Collections.emptyList();
        List<List<Game>> gamesFinalList = new ArrayList<>();

        for (List<Game> gameList : gamesLists) {
            gameLoop:
            for (Game game : gameList) {
                for (List<Game> sameGames : gamesFinalList) {
                    if (sameGames.contains(game))
                        continue gameLoop;
                }
                List<Game> sameGames = new ArrayList<>();
                sameGames.add(game);

                gamesFinalList.add(sameGames);

                for (List<Game> gameList2 : gamesLists) {
                    if (gameList != gameList2) {
                        for (Game game2 : gameList2) {
                            if (areGamesTheSame(game, game2))
                                sameGames.add(game2);
                        }
                    }
                }
            }
        }
        return gamesFinalList;
    }

    private static boolean areGamesTheSame(Game game, Game game2) {

        String[] game1Names = game.getNames();
        String[] game2Names = game2.getNames();

        return (game1Names[0].equals(game2Names[0]) && game1Names[1].equals(game2Names[1])) ||
                (game1Names[0].equals(game2Names[1]) && game1Names[1].equals(game2Names[0]));

    }

    private static boolean areIdenticalMatches(Game g1, Game g2) {
        String name1g1 = g1.getNames()[0];
        String name2g1 = g1.getNames()[1];
        String name1g2 = g2.getNames()[0];
        String name2g2 = g2.getNames()[1];

        char[] arrayName1g1 = name1g1.toCharArray();
        char[] arrayName2g1 = name2g1.toCharArray();
        char[] arrayName1g2 = name1g2.toCharArray();
        char[] arrayName2g2 = name2g2.toCharArray();

        int numberOfChar = arrayName1g1.length + arrayName2g1.length
                + arrayName1g2.length + arrayName2g2.length;
        int count = 0;

        if (arrayName1g1.length <= arrayName1g2.length) {
            for (int i = 0; i < arrayName1g1.length; i++) {
                if (arrayName1g1[i] == arrayName1g2[i]) count++;
            }
        } else {
            for (int i = 0; i < arrayName1g2.length; i++){
                if (arrayName1g1[i] == arrayName1g2[i]) count++;
            }
        }

        if (arrayName2g1.length <= arrayName2g2.length) {
            for (int i = 0; i < arrayName2g1.length; i++) {
                if (arrayName2g1[i] == arrayName2g2[i]) count++;
            }
        } else {
            for (int i = 0; i < arrayName2g2.length; i++){
                if (arrayName2g1[i] == arrayName2g2[i]) count++;
            }
        }

        return (double) count / numberOfChar >= 0.35;
    }
}
