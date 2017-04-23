package com.example.william.earthdayapplication;


import android.content.Intent;
import android.view.KeyEvent;
import  android.view.View.OnKeyListener;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;

import android.app.ProgressDialog;

import android.media.MediaPlayer;
import android.net.Uri;

import android.graphics.drawable.Drawable;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;
import java.net.URLConnection;

import com.example.william.earthdayapplication.data.Channel;
import com.example.william.earthdayapplication.data.Item;
import com.example.william.earthdayapplication.service.WeatherCB;
import com.example.william.earthdayapplication.service.YahooWeather;
import com.example.william.earthdayapplication.SettingActivity;

public class MainActivity extends AppCompatActivity implements WeatherCB {

    private EditText UserInput;
    private ImageView weatherPic;
    private TextView Temperature;
    private TextView Condition;
    private TextView Location;

    private YahooWeather service;
    private Button SearchButton;
    private ProgressDialog progress;
    private ImageView yahoo;

    private Switch VibrateSwitch;
    private Switch MusicSwitch;

    private int temp;
    private MediaPlayer mySound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserInput = (EditText) findViewById(R.id.UserInput);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        weatherPic = (ImageView) findViewById(R.id.weather_picture);
        Temperature = (TextView) findViewById(R.id.Temperature);
        Condition = (TextView) findViewById(R.id.Condition);
        Location = (TextView) findViewById(R.id.Location);
        yahoo = (ImageView) findViewById(R.id.yahoo);
        VibrateSwitch = (Switch) findViewById(R.id.MusicSwitch);
        MusicSwitch = (Switch) findViewById(R.id.MusicSwitch);
        mySound = MediaPlayer.create(this, R.raw.john_harrison_4_seasons_vivaldi);

        service = new YahooWeather(this);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading . . .");

        service.refreshWeather("Arlington, TX");

        MusicSwitch = (Switch) findViewById(R.id.MusicSwitch);
        MusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                    mySound.start();
                } else {
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                    mySound.pause();;
                }
            }
        });

        progress.show();

        // Set a key listener callback so that users can search by pressing "Enter"
        UserInput.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            searchButton(v);
                            return true;
                    }
                        String input = UserInput.getText().toString();
                        if (input.contains("\n")) {
                            searchButton(v);
                            return true;
                            }
                    }
                    return false;
            }
        });

    }


    @Override
    public void servicePass(Channel channel) {
        progress.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/cloud" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawble = getResources().getDrawable(resourceId);

        weatherPic.setImageDrawable(weatherIconDrawble);



        Temperature.setText(item.getCondition().getTemperature()+"\u00B0" + channel.getUnits().getTemperature());//temperature in F
        Condition.setText(item.getCondition().getDescription());
        Location.setText(service.getLocation());

        ////////////// Do stuff with temperature ////////////////////

        temperatureAttr(item.getCondition().getTemperature());
        temp = item.getCondition().getTemperature();

    }
    public void AlertVibrate() {
        Vibrator vibrator;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(300);
    }

    public void temperatureAttr(int temperature){
        if(temperature < 33){
            AlertVibrate();
            Toast.makeText(this, "Tender plants will die from this cold weather. Bring them in!", Toast.LENGTH_LONG).show();
        }if (temperature < 29){
            AlertVibrate();
            Toast.makeText(this, "Tender plants, fruits, and moderately hard plants will die from this cold weather. Bring them in!", Toast.LENGTH_LONG).show();
        }if (temperature > 89) {
            AlertVibrate();
            Toast.makeText(this, "Your non-desert biome plants require slightly more water!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void serviceFail(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        Temperature.setText(exception.getMessage());
        progress.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void searchButton(View view){
        if(UserInput != null && !UserInput.equals("")){
            service.refreshWeather(UserInput.getText().toString());
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            //UserInput.setVisibility(View.GONE);
           // progress.show();
        }
    }




    public void composeMmsMessage(String message, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
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
            Intent toy = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(toy);



        }
        if (itemClicked == R.id.item_Help) {


        }

        if (itemClicked == R.id.item_Send) {
            if(temp < 33){
                String message = "Your tender plants will die from this cold weather. Bring them in!";
                Uri webpage = Uri.parse(message);
                composeMmsMessage(message, webpage);
            }else if (temp < 29){
                String message = "Your fruits and moderately hard plants from this cold weather. Bring them in!";
                Uri webpage = Uri.parse(message);
                composeMmsMessage(message, webpage);
            }else if (temp > 89) {
                String message = "Your non-desert biome plants require slightly more water!";
                Uri webpage = Uri.parse(message);
                composeMmsMessage(message, webpage);
            }else{
                String message = "Play outside, plant a tree, be green and mean!!!";
                Uri webpage = Uri.parse(message);
                composeMmsMessage(message, webpage);
            }

        }
  
        return super.onOptionsItemSelected(item);
    }


}




