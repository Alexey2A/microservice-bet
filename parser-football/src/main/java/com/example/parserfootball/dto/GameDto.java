package com.example.parserfootball.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class GameDto {
    private String[] commonNames;

    public String[] getOriginalNames() {
        return originalNames;
    }

    public void setOriginalNames(String[] originalNames) {
        this.originalNames = originalNames;
    }

    private String[] originalNames;
    private String dateTime;
    private Map<String, Double> coef;
    private String webSite;

    public static final String[] RESULTS = {"П1", "Х", "П2"};

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");

    public GameDto(String[] commonNames, String[] originalNames,
                   String dateTime, Map<String, Double> coef, String webSite) {
        this.commonNames = commonNames;
        this.originalNames = originalNames;
        this.dateTime = dateTime;  // TODO исправить!!!
        this.coef = coef;
        this.webSite = webSite;
    }

    public GameDto(String webSite){
        this.commonNames = new String[] {"Неизвестная команда 1", "Неизвестная команда 2"};
        this.originalNames = new String[] {"Неизвестная команда 1", "Неизвестная команда 2"};
        this.dateTime = formatter.format(ZonedDateTime.now());  // TODO исправить!!!
        this.coef = Map.of("П1", 1.0, "Х",1.0,"П2",1.0);
        this.webSite = webSite;
    }

    public String[] getCommonNames() {
        return commonNames;
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
        GameDto game = (GameDto) o;
        return Arrays.equals(commonNames, game.commonNames);
                //&&
                //dateTime.equals(game.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commonNames);
                //, dateTime);
    }

    @Override
    public String toString() {
        return dateTime + " " + getCommonName() + " " + coef + " " + getWebSite();
    }

    public String getCommonName() {
        return commonNames[0] + " - " + commonNames[1];
    }

    public String getOriginalName() {
        return originalNames[0] + " - " + originalNames[1];
    }
}
