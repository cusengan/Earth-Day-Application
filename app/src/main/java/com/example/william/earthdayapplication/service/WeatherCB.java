package com.example.william.earthdayapplication.service;

/**
 * Created by Collooso on 4/22/2017.
 */
import com.example.william.earthdayapplication.data.Channel;

import org.json.JSONObject;

public interface WeatherCB {
    void servicePass(Channel channel);
    void serviceFail(Exception exception);
}
