package com.shakenXstirred.cocktail;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class SelectionActivity extends ActionBarActivity {

    private ListView llv;
    private ListView mlv;
    private ArrayList<Ingredient> liquors;
    private ArrayList<Ingredient> mixers;
    private ArrayList<Drink> drinks;
    private Set<String> ownedIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_selection);

        // Creates two list views associated with those in activity_selection.xml
        llv = (ListView) findViewById(R.id.liquorLV);
        mlv = (ListView) findViewById(R.id.mixersLV);

        //NEW SHIT
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        //END NEW SHIT

        // Instantiating two array lists for liquor and mixers
        liquors = new ArrayList<Ingredient>();
        mixers = new ArrayList<Ingredient>();
        drinks = new ArrayList<Drink>();
        ownedIngredients = new HashSet<String>();

        liquors.add(new Ingredient("vodka", 0xE0E0E0));
        liquors.add(new Ingredient("rum", 0xCC5000));
        liquors.add(new Ingredient("tequila", 0xFFCC00));
        liquors.add(new Ingredient("whiskey", 0xFFA319));
        liquors.add(new Ingredient("gin", 0xEBFFFF));
        liquors.add(new Ingredient("white wine", 0xFFE066));
        liquors.add(new Ingredient("coffee liqueur", 0x916C47));
        liquors.add(new Ingredient("red wine", 0x990000));

        mixers.add(new Ingredient("tonic", 0xFFFFCC));
        mixers.add(new Ingredient("sugar", 0xF5FFFF));
        mixers.add(new Ingredient("bitters", 0x400000));
        mixers.add(new Ingredient("milk", 0xFFFFE0));
        mixers.add(new Ingredient("vermouth", 0xCC3400));
        mixers.add(new Ingredient("water", 0xE0FFFF));
        mixers.add(new Ingredient("lime juice", 0x009900));
        mixers.add(new Ingredient("ginger ale", 0xFFBB22));
        mixers.add(new Ingredient("orange juice", 0xFFA200));
        mixers.add(new Ingredient("gatorade", 0x0066FF));
        mixers.add(new Ingredient("coke", 0x472400));
        mixers.add(new Ingredient("simple syrup", 0xFFFFE6));
        mixers.add(new Ingredient("lemon juice", 0xFFFF19));
        mixers.add(new Ingredient("grenadine", 0x8D1919));
        mixers.add(new Ingredient("triple sec", 0xFFEBCC));

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


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        final IngredientArrayAdapter liquorArrayAdapter = new IngredientArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                liquors );
        final IngredientArrayAdapter mixersArrayAdapter = new IngredientArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                mixers );

        //set adapters
        llv.setAdapter(liquorArrayAdapter);
        mlv.setAdapter(mixersArrayAdapter);


        //click listeners
        llv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                liquors.get(position).togglePossession();
                liquorArrayAdapter.notifyDataSetChanged();
                if(liquors.get(position).hasItem())
                    ownedIngredients.add(liquors.get(position).getName());
                else
                    ownedIngredients.remove(liquors.get(position).getName());
            }
        });
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mixers.get(position).togglePossession();
                mixersArrayAdapter.notifyDataSetChanged();
                if(mixers.get(position).hasItem())
                    ownedIngredients.add(mixers.get(position).getName());
                else
                    ownedIngredients.remove(mixers.get(position).getName());
            }
        });


    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.selection, menu);
        return true;
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

    //NEW SHIT STARTING HERE*****************************************************************
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if(mAccel > 10){
                System.out.println("shaken");
                String toMake = (drinkToMake(ownedIngredients, drinks));
                Intent intent = new Intent(SelectionActivity.this, OtherActivity.class);
                intent.putExtra("drank", toMake);
                startActivity(intent);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
    public String drinkToMake(Set<String> ingredientsOwned, ArrayList<Drink> drinkss){
        int x = 7;
        //Set missing[], correlate missing to drinks
        int[] missing = new int[drinkss.size()];
        for(int i=0; i<drinkss.size(); i++){
            missing[i] = drinkss.get(i).getFixins().length;
            for(int j=0; j<drinkss.get(i).getFixins().length; j++){
                Iterator iter = ingredientsOwned.iterator();
                while (iter.hasNext()) {
                    if(drinkss.get(i).getFixins()[j] == iter.next()){
                        missing[i]--;
                    }
                }
            }
        }

        //find lowest missing
        int min = missing[0];
        for(int i=1; i<missing.length; i++){
            if(missing[i] < min)
                min = missing[i];
        }

        //arrayList of lowest missing
        ArrayList<Integer> lowestMissingIndex = new ArrayList<Integer>();
        for(int i=0; i<missing.length; i++) {
            if(missing[i]==min)
                lowestMissingIndex.add(i);
        }

        //pick random index and ship it
        Random r = new Random();
        int i = r.nextInt(lowestMissingIndex.size());
        i = lowestMissingIndex.get(i);

        return drinkss.get(i).getName();
    }
}
