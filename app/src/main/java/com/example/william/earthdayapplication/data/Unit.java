package com.example.william.earthdayapplication.data;

import org.json.JSONObject;

/**
 * Created by Collooso on 4/22/2017.
 */

public class Unit implements JSON_populator {

   private String temperature;

   public String getTemperature() {
      return temperature;
   }

   // @Overrride
   public void populate(JSONObject data){
      temperature = data.optString("temperature");
   }
}
