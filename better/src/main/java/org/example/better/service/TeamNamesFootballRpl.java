package org.example.better.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamNamesFootballRpl {
    List<String> Spartak = new ArrayList<>(Arrays.asList("Спартак","Спартак М", "Спартак Мск", "Спартак Москва"));
    List<String> Zenit = new ArrayList<>(Arrays.asList("Зенит Ст.Петербург","Зенит", "Зенит СПб", "Зенит С-Петербург", "Зенит С-Пб"));
    List<String> CSKA = new ArrayList<>(Arrays.asList("ЦСКА М","ЦСКА", "ЦСКА Москва", "ЦСКА М", "ЦСКА Москва"));

    public static String comparisonOfTeamNames(String teamName) {
        if (teamName.equals("Спартак") || teamName.equals("Спартак М") ||
                teamName.equals("Спартак Мск") || teamName.equals("Спартак Москва"))
            return "Спартак М";
        if (teamName.equals("Зенит Ст.Петербург") || teamName.equals("Зенит") ||
                teamName.equals("Зенит СПб") || teamName.equals("Зенит С-Петербург") || teamName.equals("Зенит С-Пб"))
            return "Зенит СПб";
        if (teamName.equals("ЦСКА М") || teamName.equals("ЦСКА") ||
                teamName.equals("ЦСКА Москва") || teamName.equals("ЦСКА М") || teamName.equals("ЦСКА Москва"))
            return "ЦСКА М";
        else return  teamName;
    }
}
