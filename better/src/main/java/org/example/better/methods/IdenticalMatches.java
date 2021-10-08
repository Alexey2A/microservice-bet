package org.example.better.methods;

import org.example.better.dto.Game;


public class IdenticalMatches {

    public static boolean comparisonMatches (Game g1, Game g2) {
        String g1nameGame1 = g1.getNames()[0];
        String g1nameGame2 = g1.getNames()[1];

        String g2nameGame1 = g2.getNames()[0];
        String g2nameGame2 = g2.getNames()[1];

        char[] array11 = g1nameGame1.toCharArray();
        char[] array12 = g1nameGame2.toCharArray();

        char[] array21 = g2nameGame1.toCharArray();
        char[] array22 = g2nameGame2.toCharArray();

        int x = array11.length + array12.length + array21.length + array22.length;

        int count = 1;

        if ( array11.length <= array21.length) {
            for (int i = 0; i < array11.length; i++){
                if (array11[i] == array21[i]) count++;
            }
        } else for (int i = 0; i < array21.length; i++){
            if (array11[i] == array21[i]) count++;
        }

        if ( array12.length <= array22.length) {
            for (int i = 0; i < array12.length; i++){
                if (array12[i] == array22[i]) count++;
            }
        } else for (int i = 0; i < array22.length; i++){
            if (array12[i] == array22[i]) count++;
        }

        return x / count < x / 2;
    }
}
