package com.example.parserfootball.service;

import com.example.bet.common.entity.Bookmaker;
import com.example.bet.common.entity.Event;
import com.example.bet.common.entity.Fork;
import com.example.bet.common.entity.Game;
import com.example.bet.common.repository.*;
import com.example.parserfootball.dto.GameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class ForkService {
    private final static List<String> URL_FOOTBALL_Winline = List.of(//"https://winline.ru/stavki/sport/futbol/evropa/portugaliya/premijer_liga/",    // Португалия, премьер-лига
            "https://winline.ru/stavki/sport/futbol/rossiya/rpl/"          // РПЛ
            //"https://winline.ru/stavki/sport/futbol/angliya/apl/",          //Англия. Премьер Лига
            //"https://winline.ru/stavki/sport/futbol/angliya/liga_1/",       // Англия. Лига 1
            //"https://winline.ru/stavki/sport/futbol/germaniya/bundesliga/", //Германия. Бундеслига
            //"https://winline.ru/stavki/sport/futbol/italiya/seriya_a/",     //Италия. Серия А
            //"https://winline.ru/stavki/sport/futbol/francziya/liga_1/",     //Франция Лига 1
            //"https://winline.ru/stavki/sport/futbol/ispaniya/primera/",     // Испания
            //"https://winline.ru/stavki/sport/futbol/evropa/niderlandy/vysshaya_liga/"  //Нидерланды. Эредивизи
            //"https://winline.ru/stavki/sport/futbol/mezhdunarodnye_/mezhdunarodnye_(kluby)/liga_chempionov/"   // Лига чемпионов
    );

    private final static List<String> URL_FOOTBALL_Fonbet = List.of(//"https://www.fonbet.ru/sports/football/11939/",  // Португалия, премьер-лига
            "https://www.fonbet.ru/bets/football/11935"         // РПЛ
            //"https://www.fonbet.ru/sports/football/11918/",     //Англия. Премьер Лига
            //"https://www.fonbet.ru/sports/football/12983/",     // Англия. Лига 1
            //"https://www.fonbet.ru/sports/football/11916/",     //Германия. Бундеслига
            //"https://www.fonbet.ru/sports/football/11924/",     //Италия. Серия А
            //"https://www.fonbet.ru/sports/football/11920/",      //Франция Лига 1
            //"https://www.fonbet.ru/sports/football/11922/",      // Испания
            //"https://www.fonbet.ru/sports/football/12967/"      //Нидерланды. Эредивизи
            //"https://www.fonbet.ru/sports/football/13340/"     // Лига чемпионов
    );
    private final static List<String> URL_FOOTBALL_Olimpbet = List.of(//"https://www.olimp.bet/line/1/14233",  // Португалия, премьер-лига
            "https://www.olimp.bet/line/1/13802"     // РПЛ
            //"https://www.olimp.bet/line/1/13754",     //Англия. Премьер Лига
            //"https://www.olimp.bet/line/1/11678",     // Англия. Лига 1
            //"https://www.olimp.bet/line/1/13779",     //Германия. Бундеслига
            //"https://www.olimp.bet/line/1/14079",     //Италия. Серия А
            //"https://www.olimp.bet/line/1/13788",     //Франция Лига 1
            //"https://www.olimp.bet/line/1/13782",     //Испания
            //"https://www.olimp.bet/line/1/13818"     //Нидерланды. Эредивизи
            //"https://www.olimp.bet/live/1/14905"    // Лига чемпионов
    );
    private final static String URL_FOOTBALL_LigaStavok = "https://www.ligastavok.ru/bets/my-line/soccer/rossiia-id-350/rossiiskaia-premer-liga-id-5271";
    private final static List<String> URL_FOOTBALL_PariMatch = List.of(//"https://www.parimatch.ru/ru/football/primeira-liga-c2fc983af0c643be85e60663d28585ce/prematch",    // Португалия, премьер-лига
            "https://www.parimatch.ru/ru/football/premier-league-7ea2177607bc434a9209b6ff63eb9a90/prematch" // РПЛ
//            "https://www.parimatch.ru/ru/football/premier-league-7f5506e872d14928adf0613efa509494/prematch", //Англия. Премьер Лига
//            "https://www.parimatch.ru/ru/football/league-1-f8bee2c7dcdb40fdb604f1a5c14976b6/prematch",       // Англия. Лига 1
//            "https://www.parimatch.ru/ru/football/bundesliga-966112317e2c4ee28d5a36df840662d6/prematch",     //Германия. Бундеслига
//            "https://www.parimatch.ru/ru/football/serie-a-6d80f3f3fa35431b80d50f516e4ce075/prematch",        //Италия. Серия А
//            "https://www.parimatch.ru/ru/football/ligue-1-254e4ecf1eb84a73b37b9cedffac646d/prematch",        //Франция Лига 1
//            "https://www.parimatch.ru/ru/football/laliga-d84ce93378454b0fa61d58b2696a950b/prematch",         //Испания
//            "https://www.parimatch.ru/ru/football/eredivisie-00bf4eb20b8d4ad8b43b46fa5dda5be1/prematch"    //Нидерланды. Эредивизи
            //"https://www.parimatch.ru/ru/football/group-stage-178f2b0cf13e49b68e1795ef22c2825a/live"       // Лига чемпионов
    );
    private final static List<String> URL_FOOTBALL_Melbet = List.of(//"https://melbet.ru/line/football/118663-portugal-primeira-liga/",     // Португалия, премьер-лига
            //"https://melbet.ru/line/football/225733-russia-premier-league/"    //РПЛ
            //"https://melbet.ru/line/football/88637-england-premier-league/",    // Англия. Премьер Лига
            //"https://melbet.ru/line/football/13709-england-league-one/",        //Англия. Лига 1
            //"https://melbet.ru/line/football/96463-germany-bundesliga/",        //Германия. Бундеслига
            //"https://melbet.ru/line/football/110163-italy-serie-a/",            //Италия. Серия А
            //"https://melbet.ru/line/football/12821-france-ligue-1/",            //Франция Лига 1
            //"https://melbet.ru/line/football/127733-spain-la-liga/",            //Испания
            //"https://melbet.ru/line/football/2018750-netherlands-eredivisie/"  //Нидерланды. Эредивизи
            //"https://melbet.ru/line/football/118587-uefa-champions-league/"    //Лига чемпионов
    );

    private final ParserFootballLigastavok parserFootballLigastavok;
    private final ParserFootballOlimpbet parserFootballOlimpbet;
    private final ParserFootballWinline parserFootballWinline;
    private final ParserFootballPariMatch parserFootballPariMatch;
    private final ParserFootballFonbet parserFootballFonbet;
    private final ParserFootballMelbet parserFootballMelbet;

    private final double fork;

    private final BookmakerRepository bookmakerRepository;
    private final ForkRepository forkRepository;
    private final GameRepository gameRepository;
    private final BetRepository betRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ForkService(ParserFootballLigastavok parserFootballLigastavok, ParserFootballOlimpbet parserFootballOlimpbet,
                       ParserFootballWinline parserFootballWinline, ParserFootballPariMatch parserFootballPariMatch,
                       ParserFootballFonbet parserFootballFonbet, ParserFootballMelbet parserFootballMelbet,
                       @Value("${fork.value}") double fork, BookmakerRepository bookmakerRepository, ForkRepository forkRepository,
                       GameRepository gameRepository, BetRepository betRepository, EventRepository eventRepository) {
        this.parserFootballLigastavok = parserFootballLigastavok;
        this.parserFootballOlimpbet = parserFootballOlimpbet;
        this.parserFootballWinline = parserFootballWinline;
        this.parserFootballPariMatch = parserFootballPariMatch;
        this.parserFootballFonbet = parserFootballFonbet;
        this.parserFootballMelbet = parserFootballMelbet;
        this.fork = fork;
        this.bookmakerRepository = bookmakerRepository;
        this.forkRepository = forkRepository;
        this.gameRepository = gameRepository;
        this.betRepository = betRepository;
        this.eventRepository = eventRepository;
    }

    public List<String> parserStart() throws InterruptedException, ExecutionException {

        List<List<GameDto>> games = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Future<List<GameDto>> foolballWinlineResult = executorService.submit(() -> {
            System.out.println("WINLINE");
            List<GameDto> gamesFootballWinline = new ArrayList<>();
            for (String url : URL_FOOTBALL_Winline) {
                gamesFootballWinline.addAll(parserFootballWinline.getGames(url));
            }
            saveBookmaker("WINLINE", "https://winline.ru/");

            return gamesFootballWinline;
        });

        Future<List<GameDto>> footballFonbetResult = executorService.submit(() -> {
            System.out.println("FONBET");
            List<GameDto> gamesFootballFonbet = new ArrayList<>();
            for (String url : URL_FOOTBALL_Fonbet) {
                gamesFootballFonbet.addAll(parserFootballFonbet.getGames(url));
            }
            saveBookmaker("FONBET", "https://www.fonbet.ru/");

            return gamesFootballFonbet;
        });

        Future<List<GameDto>> foolballOlimpbetResult = executorService.submit(() -> {
            System.out.println("OLIMPBET");
            List<GameDto> gamesFootballOlimpbet = new ArrayList<>();
            for (String url : URL_FOOTBALL_Olimpbet) {
                gamesFootballOlimpbet.addAll(parserFootballOlimpbet.getGames(url));
            }
            saveBookmaker("OLIMPBET", "https://www.olimp.bet/");

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

        Future<List<GameDto>> foolballPariMatchResult = executorService.submit(() -> {
            System.out.println("PariMatch");
            List<GameDto> gamesFootballPariMatch = new ArrayList<>();
            for (String url : URL_FOOTBALL_PariMatch) {
                gamesFootballPariMatch.addAll(parserFootballPariMatch.getGames(url));
            }
            saveBookmaker("PariMatch", "https://www.parimatch.ru/");

            return gamesFootballPariMatch;
        });

        Future<List<GameDto>> footballMelbetResult = executorService.submit(() -> {
            System.out.println("Melbet");
            List<GameDto> gamesFoolballMelbet = new ArrayList<>();
            for (String url : URL_FOOTBALL_Melbet) {
                gamesFoolballMelbet.addAll(parserFootballMelbet.getGames(url));
            }
            saveBookmaker("Melbet", "https://melbet.ru/");

            return gamesFoolballMelbet;
        });

// заменить поля ниже одним списком
        var gamesFootballWinline = foolballWinlineResult.get();
        var gamesFootballOlimpbet = foolballOlimpbetResult.get();
        //var gamesFootballLigastavok = foolballLigastavokResult.get();
        var gamesFootballPariMatch = foolballPariMatchResult.get();
        var gamesFootballFonbet = footballFonbetResult.get();
        var gamesFoolballMelbet = footballMelbetResult.get();


        List<String> listOfForkChecksForTwoAndThreeMatches = new ArrayList<>();

        for (List<GameDto> gameDtoList : sameGamesList(gamesFootballOlimpbet, gamesFootballPariMatch,      // поиск вилки на двух сайтах
                gamesFootballWinline, gamesFootballFonbet, gamesFoolballMelbet)) {
            for (GameDto g1 : gameDtoList) {
                double[] coefficients = {g1.getCoef().get("П1"), g1.getCoef().get("П2"), g1.getCoef().get("Х")};
                for (int i = 0; i < coefficients.length; i++) {
                    for (GameDto g2 : gameDtoList) {
                        if (g1 != g2) {
                            double[] coefficients2 = {g2.getCoef().get("П1"), g2.getCoef().get("П2"), g2.getCoef().get("Х")};
                            double result = 1 / coefficients[i];
                            double coefficient2 = 1;
                            for (int j = 0; j < coefficients2.length; j++) {
                                if (j != i) {
                                    result += 1 / coefficients2[j];
                                    coefficient2 = coefficients2[j];
                                }
                            }
                            if (result < fork) {
                                // Создаем Game если такая игра отсутствует
                                Optional<Game> gameOptional = gameRepository.findByNameAndDateTime(g1.getCommonName(), g1.getDateTime());
                                if (gameOptional.isEmpty()) {
                                    gameRepository.save(new Game(g1.getCommonName(), g1.getDateTime()));
                                }

                               /* if ()
                                Game game = new Game(g1);*/

                                String string = "Проверка вилки: " + result + " " +
                                        g1 + " " + coefficients[i] + " " + g2 + coefficient2 + " " + i + "\n";
                                listOfForkChecksForTwoAndThreeMatches.add(string);
                                //System.out.printf("Проверка вилки: %f %s %s %d %n", result, game, game2, i);
                            }
                        }
                    }
                }
            }
        }
        for (List<GameDto> g : sameGamesList(gamesFootballOlimpbet,                // поиск вилки на трёх сайтах
                gamesFootballPariMatch,
                gamesFootballWinline,
                gamesFootballFonbet,
                gamesFoolballMelbet)) {
            System.out.println(g);
            for (GameDto game : g) {
                double[] coefficients = {game.getCoef().get("П1"), game.getCoef().get("П2"), game.getCoef().get("Х")};
                for (int i = 0; i < coefficients.length; i++) {
                    for (GameDto game2 : g) {
                        if (game != game2) {
                            double[] coefficients2 = {game2.getCoef().get("П1"), game2.getCoef().get("П2"), game2.getCoef().get("Х")};
                            for (GameDto game3 : g) {
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
                                            if (result < fork) {
                                                String string = new String("Проверка вилки: " + result + " " +
                                                        game + " " + coefficients[i] + " " + game2 + " " + game3 + " " + i + "\n");
                                                listOfForkChecksForTwoAndThreeMatches.add(string);
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
        }
        return listOfForkChecksForTwoAndThreeMatches;
    }

    private void saveBookmaker(String nameSite, String url) {
        if (bookmakerRepository.findByName(nameSite).isEmpty()) {
            Bookmaker bookmaker = new Bookmaker(nameSite, url);
            bookmakerRepository.save(bookmaker);
        }
    }

    private void saveEvent(long bookmakerId, long gameId, String name) {

    }

    private void saveFork(Double margin) {
        Fork fork = new Fork(margin);
        forkRepository.save(fork);
    }

    public static List<List<GameDto>> sameGamesList(List<GameDto>... gamesLists) {
        if (gamesLists == null || gamesLists.length == 0)
            return Collections.emptyList();
        List<List<GameDto>> gamesFinalList = new ArrayList<>();

        for (List<GameDto> gameList : gamesLists) {
            gameLoop:
            for (GameDto game : gameList) {
                for (List<GameDto> sameGames : gamesFinalList) {
                    if (sameGames.contains(game))
                        continue gameLoop;
                }
                List<GameDto> sameGames = new ArrayList<>();
                sameGames.add(game);

                gamesFinalList.add(sameGames);

                for (List<GameDto> gameList2 : gamesLists) {
                    if (gameList != gameList2) {
                        for (GameDto game2 : gameList2) {
                            if (areGamesTheSame(game, game2))
                                sameGames.add(game2);
                        }
                    }
                }
            }
        }
        return gamesFinalList;
    }

    private static boolean areGamesTheSame(GameDto game, GameDto game2) {

        String[] game1Names = game.getCommonNames();
        String[] game2Names = game2.getCommonNames();

        return (game1Names[0].equals(game2Names[0]) && game1Names[1].equals(game2Names[1])) ||
                (game1Names[0].equals(game2Names[1]) && game1Names[1].equals(game2Names[0]));

    }
}
