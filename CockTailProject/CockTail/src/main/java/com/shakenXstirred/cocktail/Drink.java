package com.shakenXstirred.cocktail;

/**
 * Created by nick on 19/01/14.
 */
public class Drink {
    private String name;
    private String[] fixins;
    private int[] ratios;

    public Drink(String s, String[] f, int[] r) {
        name = s;
        fixins = f;
        ratios = r;
    }

    public int[] getRatios() {
        return ratios;
    }

    public String getName() {
        return name;
    }

    public String[] getFixins() {
        return fixins;
    }
}
