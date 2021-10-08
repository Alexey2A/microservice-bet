package org.example.better.service;

import org.example.better.dto.Game;
import org.example.better.methods.IdenticalMatches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ForkService {
    private final static String URL_FOOTBALL_Winline = "https://whatismyipaddress.com/";
    private final static String URL_FOOTBALL_Fonbet = "https://www.fonbet.ru/sports/football/11935";
    private final static String URL_FOOTBALL_Olimpbet = "https://www.olimp.bet/line/1/13802";
    private final static String URL_FOOTBALL_LigaStavok = "https://www.ligastavok.ru/bets/my-line/soccer/rossiia-id-350/rossiiskaia-premer-liga-id-5271";
    private final static String URL_FOOTBALL_PariMatch = "https://www.parimatch.ru/ru/football/premier-league-7ea2177607bc434a9209b6ff63eb9a90/prematch";

    private final ParserFootballLigastavok parserFootballLigastavok;
    private final ParserFootballOlimpbet parserFootballOlimpbet;
    private final ParserFootballWinline parserFootballWinline;
    private final ParserFootballPariMatch parserFootballPariMatch;
    private final ParserFootballFonbet parserFootballFonbet;

    @Autowired
    public ForkService(ParserFootballLigastavok parserFootballLigastavok,
                       ParserFootballOlimpbet parserFootballOlimpbet,
                       ParserFootballWinline parserFootballWinline,
                       ParserFootballPariMatch parserFootballPariMatch,
                       ParserFootballFonbet parserFootballFonbet) {
        this.parserFootballLigastavok = parserFootballLigastavok;
        this.parserFootballOlimpbet = parserFootballOlimpbet;
        this.parserFootballWinline = parserFootballWinline;
        this.parserFootballPariMatch = parserFootballPariMatch;
        this.parserFootballFonbet = parserFootballFonbet;
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

        Future<List<Game>> foolballFonbetResult = executorService.submit(() -> {
            System.out.println("FONBET");
            List<Game> gamesFootballFonbet = parserFootballFonbet.getGames(URL_FOOTBALL_Fonbet);

            return gamesFootballFonbet;
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

        Future<List<Game>> foolballLigastavokResult = executorService.submit(() -> {
            System.out.println("LigaStavok");
            List<Game> gamesFootballLigastavok = parserFootballLigastavok.getGames(URL_FOOTBALL_LigaStavok);
           games.add(gamesFootballLigastavok);
            for (Game g : gamesFootballLigastavok) {
                System.out.println(g);
            }
            System.out.println(gamesFootballLigastavok.size());
            System.out.println("----------");
            return gamesFootballLigastavok;
        });

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
        var gamesFootballLigastavok = foolballLigastavokResult.get();
        var gamesFootballPariMatch = foolballPariMatchResult.get();
        var gamesFootballFonbet = foolballFonbetResult.get();


        List<String> listOfForkChecksForTwoMatches = new ArrayList<>();
        List<String> listOfForkChecksForThreeMatches = new ArrayList<>();

        /*for (List<Game> g : sameGamesList(//gamesFootballLigastavok,
                                            gamesFootballOlimpbet,
                                            gamesFootballPariMatch,
                                            gamesFootballWinline,
                                            gamesFootballFonbet)) {
            System.out.println(g);
            for (Game game : g) {
                double[] coefficients = {game.getCoef().get("П1"), game.getCoef().get("П2"), game.getCoef().get("Х")};
                for (int i = 0; i < coefficients.length; i++) {
                    for (Game game2 : g) {
                        if (game != game2) {
                            double[] coefficients2 = {game2.getCoef().get("П1"), game2.getCoef().get("П2"), game2.getCoef().get("Х")};
                            double result = 1 / coefficients[i];
                            for (int j = 0; j < coefficients2.length; j++) {
                                if (j != i) {
                                    result += 1 / coefficients2[j];
                                }
                            }
                            String string = new String("Проверка вилки: " + String.valueOf(result) + " " +
                                    game + " " + coefficients[i] + " " + game2 + " " + i + "\n");
                            listOfForkChecksForTwoMatches.add(string);
                            //System.out.printf("Проверка вилки: %f %s %s %d %n", result, game, game2, i);
                        }
                    }
                }
            }
        }*/

        for (List<Game> g : sameGamesList(gamesFootballLigastavok,
                gamesFootballOlimpbet,
                gamesFootballPariMatch,
                gamesFootballWinline,
                gamesFootballFonbet)) {
            System.out.println(g);
            for (Game game : g) {
                double[] coefficients = {game.getCoef().get("П1"), game.getCoef().get("П2"), game.getCoef().get("Х")};
                for (int i = 0; i < coefficients.length; i++) {
                    for (Game game2 : g) {
                        if (game != game2) {
                            double[] coefficients2 = {game2.getCoef().get("П1"), game2.getCoef().get("П2"), game2.getCoef().get("Х")};
                            for (Game game3 : g) {
                                if (game3 != game && game3 != game2) {
                                    double[] coefficients3 = {game3.getCoef().get("П1"), game3.getCoef().get("П2"), game3.getCoef().get("Х")};

                                    double result = 1 / coefficients[i];
                                    for (int j = 0; j < coefficients2.length; j++) {
                                        if (j != i) {
                                            result += 1 / coefficients2[j];
                                            for (int k = 0; k < coefficients3.length; k++) {
                                                if (k != i && k != j) {
                                                    result += 1 / coefficients3[k];
                                                }
                                            }
                                            String string = new String("Проверка вилки: " + String.valueOf(result) + " " +
                                                    game + " " + coefficients[i] + " " + game2 + " " + game3 + " " + i + "\n");
                                            listOfForkChecksForThreeMatches.add(string);
                                            //System.out.printf("Проверка вилки: %f %s %s %d %n", result, game, game2, i);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        return listOfForkChecksForThreeMatches;
        //return listOfForkChecksForTwoMatches;
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
}
