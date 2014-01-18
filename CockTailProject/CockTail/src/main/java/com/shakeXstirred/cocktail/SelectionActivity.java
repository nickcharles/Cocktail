package com.shakeXstirred.cocktail;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SelectionActivity extends ActionBarActivity {

    private ListView Ingredientslv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Ingredientslv = (ListView) findViewById(R.id.IngredientsView);

        // Instantiating an array list (you don't need to do this,
        // you already have yours).
        ArrayList<String> IngredientsList = new ArrayList<String>();
        IngredientsList.add("foo");
        IngredientsList.add("bar");
        IngredientsList.add("bar1");
        IngredientsList.add("bar2");
        IngredientsList.add("bar3");
        IngredientsList.add("bar4");
        IngredientsList.add("bar5");
        IngredientsList.add("bar6");
        IngredientsList.add("bar7");
        IngredientsList.add("bar8");
        IngredientsList.add("bar9");
        IngredientsList.add("bar10");
        IngredientsList.add("bar11");
        IngredientsList.add("bar12");
        IngredientsList.add("bar13");
        IngredientsList.add("bar14");
        IngredientsList.add("bar15");
        IngredientsList.add("bar16");
        IngredientsList.add("bar17");
        IngredientsList.add("bar18");




///////////////////////////
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost("http://cocktail.aws.af.cm/liquors");

        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }

        try {
        JSONObject jObject = new JSONObject(result);

        System.out.println(result);
        } catch (Exception e) {
            //ghey
        }
///////////////////////////////





        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> IngredientsAdapter = new ArrayAdapter<String>(
               this,
               android.R.layout.simple_list_item_multiple_choice,
               IngredientsList );

        Ingredientslv.setAdapter(IngredientsAdapter);
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

    private ListView lv;
}
