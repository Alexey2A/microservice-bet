package com.example.parserfootball.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class Game {
    private String[] names;
    private String dateTime;
    private Map<String, Double> coef;
    private String webSite;

    public static final String[] RESULTS = {"П1", "Х", "П2"};

    public Game(String[] names, String dateTime, Map<String, Double> coef, String webSite) {
        this.names = names;
        this.dateTime = dateTime;
        this.coef = coef;
        this.webSite = webSite;

    }


    public String[] getNames() {
        return names;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Map<String, Double> getCoef() {
        return coef;
    }

    public String getWebSite() {
        return webSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Arrays.equals(names, game.names);
                //&&
                //dateTime.equals(game.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names);
                //, dateTime);
    }

    @Override
    public String toString() {
        return dateTime + " " + names[0] + " - " + names[1] + " " + coef + " " + getWebSite();
    }
}
