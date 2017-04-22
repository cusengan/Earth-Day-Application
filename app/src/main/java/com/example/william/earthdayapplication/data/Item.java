package com.example.william.earthdayapplication.data;

import org.json.JSONObject;

/**
 * Created by Collooso on 4/22/2017.
 */

public class Item implements JSON_populator{


    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void populate(JSONObject data){
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
