package com.example.william.earthdayapplication;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.william.earthdayapplication.data.Channel;
import com.example.william.earthdayapplication.service.WeatherCB;
import com.example.william.earthdayapplication.service.YahooWeather;

public class MainActivity extends AppCompatActivity implements WeatherCB {

    private TextView Temperature;
    private TextView Condition;
    private TextView Location;
    private ImageView yahoo;
    private YahooWeather service;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Temperature = (TextView) findViewById(R.id.Temperature);
        Condition = (TextView) findViewById(R.id.Condition);
        Location = (TextView) findViewById(R.id.Location);
        yahoo = (ImageView) findViewById(R.id.yahoo);
        service = new YahooWeather(this);
        service.setLocation("Dallas, TX");
        progress = new ProgressDialog(this);
        progress.setMessage("Loading . . .");
       // progress.show();

    }




    @Override
    public void servicePass(Channel channel) {
        progress.hide();

    }

    @Override
    public void serviceFail(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG);
        progress.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemClicked = item.getItemId();

        if(itemClicked == R.id.item_Setting){

            Context context = MainActivity.this;
            Intent activity_setting = new Intent(context, SettingActivity.class);
            startActivity(activity_setting);

          

        }
        if(itemClicked == R.id.item_Instruction){

        }
        return super.onOptionsItemSelected(item);
    }


}
