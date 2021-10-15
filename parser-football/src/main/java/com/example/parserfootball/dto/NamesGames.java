package com.example.parserfootball.dto;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class NamesGames {

    public static String comparisonOfTeamNames(String teamName, String nameFile)  {
        try {
            Properties properties = new Properties();
            properties.load(Files.newBufferedReader(Paths.get(nameFile), StandardCharsets.UTF_8));
            Map<String, Set<String>> map = properties.entrySet().stream().collect(Collectors.toMap(keyValue -> (String) keyValue.getKey(),
                    keyValue -> Set.of(((String) keyValue.getValue()).split(","))));
            System.out.println(properties);

            for (var kv : map.entrySet()) {
                if (kv.getValue().contains(teamName)) {
                    return kv.getKey();
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return  " ";
    }
}
