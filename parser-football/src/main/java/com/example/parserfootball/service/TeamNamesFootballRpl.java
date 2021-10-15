package com.example.parserfootball.service;

import java.util.*;

public class TeamNamesFootballRpl {    // Winline, Fonbet, Olimpbet, Parimatch
    Set<String> spartak = new HashSet<>(Arrays.asList("Спартак", "Спартак М", "Спартак Мск", "Спартак Москва"));
    Set<String> zenit = new HashSet<>(Arrays.asList("Зенит Ст.Петербург", "Зенит", "Зенит СПб"
            , "Зенит С-Петербург", "Зенит С-Пб", "Зенит Санкт-Петербург"));
    Set<String> cska = new HashSet<>(Arrays.asList("ЦСКА М", "ЦСКА", "ЦСКА Москва", "ЦСКА М", "ЦСКА Москва"));
    Set<String> arsenal = new HashSet<>(Arrays.asList("Арсенал Тула"));
    Set<String> rubin = new HashSet<>(Arrays.asList("Рубин", "Рубин Казань"));
    Set<String> lokomotiv = new HashSet<>(Arrays.asList("Локомотив М", "Локомотив Москва"));
    Set<String> dinamo = new HashSet<>(Arrays.asList("Динамо М", "Динамо Москва"));
    Set<String> sochi = new HashSet<>(Arrays.asList("Сочи", "ФК Сочи"));  // ФК Сочи - ФК Ростов
    Set<String> rostov = new HashSet<>(Arrays.asList("Ростов", "ФК Ростов"));
    Set<String> ural = new HashSet<>(Arrays.asList("Урал"));
    Set<String> khimki = new HashSet<>(Arrays.asList("Химки"));
    Set<String> akhmat = new HashSet<>(Arrays.asList("Ахмат"));
    Set<String> wingsOfTheSoviets = new HashSet<>(Arrays.asList("Крылья Советов"));
    Set<String> nn = new HashSet<>(Arrays.asList("Нижний Новгород", "Н.Новгород"));
    Set<String> krasnodar = new HashSet<>(Arrays.asList("Краснодар", "ФК Краснодар"));
    Set<String> ufa = new HashSet<>(Arrays.asList("Уфа", "ФК Уфа"));

    public String comparisonOfTeamNames(String teamName) {
        if (spartak.contains(teamName))
            return "Спартак";
        else if (zenit.contains(teamName))
            return "Зенит";
        else if (cska.contains(teamName))
            return "ЦСКА";
        else if (arsenal.contains(teamName))
            return "Арсенал Тула";
        else if (rubin.contains(teamName))
            return "Рубин";
        else if (lokomotiv.contains(teamName))
            return "Локомотив";
        else if (dinamo.contains(teamName))
            return "Динамо";
        else if (sochi.contains(teamName))
            return "Сочи";
        else if (ural.contains(teamName))
            return "Урал";
        else if (rostov.contains(teamName))
            return "Ростов";
        else if (khimki.contains(teamName))
            return "Химки";
        else if (akhmat.contains(teamName))
            return "Ахмат";
        else if (wingsOfTheSoviets.contains(teamName))
            return "Крылья Советов";
        else if (nn.contains(teamName))
            return "Нижний Новгород";
        else if (krasnodar.contains(teamName))
            return "Краснодар";
        else if (ufa.contains(teamName))
            return "Уфа";

        else return "No name!!!";
    }
}
