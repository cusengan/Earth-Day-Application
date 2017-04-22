package com.example.william.earthdayapplication.data;

import org.json.JSONObject;



/**
 * Created by Collooso on 4/22/2017.
 */

public class Channel implements JSON_populator {
    private Item item;

    public Item getItem() {
        return item;
    }

    public Unit getUnits() {
        return units;
    }

    private Unit units;


    public void populate(JSONObject data){
        units = new Unit();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

    }
}
