package com.shakenXstirred.cocktail;

/**
 * Created by glass on 18/01/14.
 */

public class Ingredient {
    private String name;
    private int color;
    private boolean userHas;

    public Ingredient(String s, int c) {
        name = s;
        color = c;
        userHas = false;
    }

    public void togglePossession() {
        userHas = !userHas;
    }

    public boolean hasItem(){
        return userHas;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
