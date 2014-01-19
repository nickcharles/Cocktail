package com.shakenXstirred.cocktail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nick on 18/01/14.
 */

public class IngredientArrayAdapter extends ArrayAdapter<Ingredient> {
    private ArrayList<Ingredient> ingredientAL;
    private Context context;

    public IngredientArrayAdapter(Context context, int resource, ArrayList<Ingredient> iAL) {
        super(context, resource, iAL);
        this.context = context;
        ingredientAL = iAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, parent, false);
        }

        Ingredient i = ingredientAL.get(position);

        if (i != null) {
            TextView text = (TextView) v.findViewById(R.id.list_item);
            if (text != null) {
                text.setText(i.getName());
                if(i.hasItem() == true)
                    text.setBackgroundColor(0xFF000000 + i.getColor());
                else
                    text.setBackgroundColor(0xFF777777);
                if(i.hasItem() == false)
                    text.setTextColor(0xFF000000);
                else
                    text.setTextColor(0xFF000000 + textColorer(i.getColor()));
            }
        }
        return v;
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
}
