package com.example.william.earthdayapplication.data;

import org.json.JSONObject;

/**
 * Created by Collooso on 4/22/2017.
 */

public class Condition implements JSON_populator {
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }



    public void populate(JSONObject data){
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
    }
}
