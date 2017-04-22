package com.example.william.earthdayapplication;


import android.content.Intent;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;

import android.app.ProgressDialog;

import android.net.Uri;

import android.graphics.drawable.Drawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;
import java.net.URLConnection;

import com.example.william.earthdayapplication.data.Channel;
import com.example.william.earthdayapplication.data.Item;
import com.example.william.earthdayapplication.service.WeatherCB;
import com.example.william.earthdayapplication.service.YahooWeather;

public class MainActivity extends AppCompatActivity implements WeatherCB {

    private ImageView weatherPic;
    private TextView Temperature;
    private TextView Condition;
    private TextView Location;

    private ScrollView scrolling;
    private YahooWeather service;

    private ProgressDialog progress;
    private ImageView yahoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherPic = (ImageView) findViewById(R.id.weather_picture);
        Temperature = (TextView) findViewById(R.id.Temperature);
        Condition = (TextView) findViewById(R.id.Condition);
        Location = (TextView) findViewById(R.id.Location);
        yahoo = (ImageView) findViewById(R.id.yahoo);



        service = new YahooWeather(this);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading . . .");

        service.refreshWeather("Dallas, TX");



        progress.show();

    }




    @Override
    public void servicePass(Channel channel) {
        progress.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/cloud" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawble = getResources().getDrawable(resourceId);

        weatherPic.setImageDrawable(weatherIconDrawble);



        Temperature.setText(item.getCondition().getTemperature()+"\u00B0" + channel.getUnits().getTemperature());
        Condition.setText(item.getCondition().getDescription());
        Location.setText(service.getLocation());

    }

    @Override
    public void serviceFail(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG);
        Temperature.setText(exception.getMessage());
        progress.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }





    public void composeMmsMessage(String message, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("smsto:4693058613"));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
        if (itemClicked == R.id.item_Help) {

            Intent toy = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(toy);
        }

        if (itemClicked == R.id.item_Send) {

//            SmsManager smsmanager =  SmsManager.getDefault();
//            smsmanager.sendTextMessage("4693058613", null, "Check this out", null, null);
                String message = "Check this out!";
                Uri webpage = Uri.parse(message);
                composeMmsMessage(message, webpage);
        }
  
        return super.onOptionsItemSelected(item);
    }


}




