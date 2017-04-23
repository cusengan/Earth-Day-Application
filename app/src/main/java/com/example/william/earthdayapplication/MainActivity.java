package com.example.william.earthdayapplication;


import android.content.Intent;
import android.media.Image;
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
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

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
    private TextView Speech2;

    private ImageView silotree1;
    private ImageView silotree2;
    private ImageView silotree3;
    private ImageView silotree4;
    private ImageView silotree5;
    private ImageView silotree6;
    private ImageView silotree7;
    private ImageView silotree8;
    private ImageView silotree9;
    private ImageView silotree10;


    private YahooWeather service;
    private Button SearchButton;
    private Button YesButton;
    private ProgressDialog progress;
    private ImageView yahoo;

    private Switch VibrateSwitch;
    private Switch MusicSwitch;

    private int temp;
    private MediaPlayer mySound;
    private ImageSwitcher sw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserInput = (EditText) findViewById(R.id.UserInput);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        YesButton = (Button) findViewById(R.id.YesButton);

        silotree1 = (ImageView) findViewById(R.id.silotree1);
        silotree2 = (ImageView) findViewById(R.id.silotree2);
        silotree3 = (ImageView) findViewById(R.id.silotree3);
        silotree4 = (ImageView) findViewById(R.id.silotree4);
        silotree5 = (ImageView) findViewById(R.id.silotree5);
        silotree6 = (ImageView) findViewById(R.id.silotree6);
        silotree7 = (ImageView) findViewById(R.id.silotree7);
        silotree8 = (ImageView) findViewById(R.id.silotree8);
        silotree9 = (ImageView) findViewById(R.id.silotree9);
        silotree10 = (ImageView) findViewById(R.id.silotree10);


        weatherPic = (ImageView) findViewById(R.id.weather_picture);
        Temperature = (TextView) findViewById(R.id.Temperature);
        Condition = (TextView) findViewById(R.id.Condition);
        Location = (TextView) findViewById(R.id.Location);
        Speech2 = (TextView) findViewById(R.id.Speech2);
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

         //Set a key listener callback so that users can search by pressing "Enter"
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
        progress.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();


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
            progress.show();
        }
    }

    public void YesButton(View view) {
        Random r = new Random();
        int num = r.nextInt(10 - 1 + 1) + 1;//(max-min+1)+1
        switch (num){
            case 1: silotree1.setVisibility(View.VISIBLE);break;
            case 2: silotree2.setVisibility(View.VISIBLE);break;
            case 3: silotree3.setVisibility(View.VISIBLE);break;
            case 4: silotree4.setVisibility(View.VISIBLE);break;
            case 5: silotree5.setVisibility(View.VISIBLE);break;
            case 6: silotree6.setVisibility(View.VISIBLE);break;
            case 7: silotree7.setVisibility(View.VISIBLE);break;
            case 8: silotree8.setVisibility(View.VISIBLE);break;
            case 9: silotree9.setVisibility(View.VISIBLE);break;
            case 10: silotree10.setVisibility(View.VISIBLE);break;
        }
        YesButton.setVisibility(View.GONE);
        Speech2.setVisibility(View.VISIBLE);
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

        if(itemClicked == R.id.item_Facts){

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
                String message = "Your tender plants will die from this cold weather. Bring them in!\n\nSent from the ";
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
                String message = "Play outside, plant a tree, be green!";
                Uri webpage = Uri.parse(message);
                composeMmsMessage(message, webpage);
            }

        }
  
        return super.onOptionsItemSelected(item);
    }


}




