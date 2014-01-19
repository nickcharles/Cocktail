package com.shakenXstirred.cocktail;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class OtherActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String drank = b.getString("drank");
        getActionBar().setTitle(drank);

        final ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        final ArrayList<Drink> drinks = new ArrayList<Drink>();

        ingredients.add(new Ingredient("vodka", 0xE0E0E0));
        ingredients.add(new Ingredient("rum", 0xCC5000));
        ingredients.add(new Ingredient("tequila", 0xFFCC00));
        ingredients.add(new Ingredient("whiskey", 0xFFA319));
        ingredients.add(new Ingredient("gin", 0xEBFFFF));
        ingredients.add(new Ingredient("white wine", 0xFFE066));
        ingredients.add(new Ingredient("coffee liqueur", 0x916C47));
        ingredients.add(new Ingredient("red wine", 0x990000));

        ingredients.add(new Ingredient("tonic", 0xFFFFCC));
        ingredients.add(new Ingredient("sugar", 0xF5FFFF));
        ingredients.add(new Ingredient("bitters", 0x400000));
        ingredients.add(new Ingredient("milk", 0xFFFFE0));
        ingredients.add(new Ingredient("vermouth", 0xCC3400));
        ingredients.add(new Ingredient("water", 0xE0FFFF));
        ingredients.add(new Ingredient("lime juice", 0x009900));
        ingredients.add(new Ingredient("ginger ale", 0xFFBB22));
        ingredients.add(new Ingredient("orange juice", 0xFFA200));
        ingredients.add(new Ingredient("gatorade", 0x0066FF));
        ingredients.add(new Ingredient("coke", 0x472400));
        ingredients.add(new Ingredient("simple syrup", 0xFFFFE6));
        ingredients.add(new Ingredient("lemon juice", 0xFFFF19));
        ingredients.add(new Ingredient("grenadine", 0x8D1919));
        ingredients.add(new Ingredient("triple sec", 0xFFEBCC));

        drinks.add(new Drink("Gin & Tonic", new String[] {"gin", "tonic"}, new int[] {1,1}));
        drinks.add(new Drink("Screwdriver", new String[] {"orange juice", "vodka"}, new int[] {2,1}));
        drinks.add(new Drink("Moscow Mule", new String[] {"vodka", "lime juice", "ginger ale"}, new int[] {2,1,3}));
        drinks.add(new Drink("Faderade", new String[] {"gatorade", "vodka"}, new int[] {3,1}));
        drinks.add(new Drink("White Russian", new String[] {"vodka", "coffee liqueur", "milk"}, new int[] {5,2,3}));
        drinks.add(new Drink("Cuba Libre", new String[] {"rum", "coke", "lime juice"}, new int[] {1,2,0}));
        drinks.add(new Drink("Manhattan", new String[] {"whiskey", "vermouth", "bitters"}, new int[] {5,1,0}));
        drinks.add(new Drink("Old Fashioned", new String[] {"whiskey", "bitters", "water", "sugar"}, new int[] {5,1,0,0}));
        drinks.add(new Drink("Martini", new String[] {"gin", "vermouth"}, new int[] {11,3}));
        drinks.add(new Drink("Daiquiri", new String[] {"rum", "lime juice", "simple syrup"}, new int[] {9,5,3}));
        drinks.add(new Drink("Tequila Sunrise", new String[] {"tequila", "orange juice", "grenadine"}, new int[] {3,6,1}));
        drinks.add(new Drink("Long Island Iced Tea", new String[] {"vodka", "tequila", "rum", "gin", "triple sec", "lemon juice", "simple syrup", "coke"}, new int[] {1,1,1,1,1,2,1,1}));

        int i=0;
        while(!(drank.equals(drinks.get(i).getName()))){//Iterate through drinks
            i++;
        }

        Ingredient[] ing = new Ingredient[ingredients.size()];
        for(int k=0; k<ingredients.size(); k++){
            ing[k] = ingredients.get(k);
        }

        LinearLayout layout = SetDrinkGUI(drinks.get(i), ing);

        setContentView(layout);
    }

    LinearLayout SetDrinkGUI(Drink drink, Ingredient[] ingridents){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        String[] fixins = drink.getFixins();
        int[] weights = drink.getRatios();
        for(int i = 0; i < fixins.length; ++i){
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, weights[i]));
            tv.setText(fixins[i]);
            tv.setTextSize(40);
            tv.setGravity(Gravity.CENTER);

            int j = 0;
            while(!(fixins[i].equals(ingridents[j].getName()))){
                j++;
            }

            tv.setBackgroundColor(0xFF000000 + ingridents[j].getColor());
            tv.setTextColor(0xFF000000 + textColorer(ingridents[j].getColor()));
            layout.addView(tv);
        }
        return layout;
    }

    private int textColorer(int background) {
        int blue = background % 0x100;
        background = background/0x100;
        int green = background % 0x100;
        background = background/0x100;
        int red = background % 0x100;
        background = background % 0x100;

        if ((red + green + blue)/3 > 0x7F)
            return 0x000000;
        else
            return 0xFFFFFF;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
