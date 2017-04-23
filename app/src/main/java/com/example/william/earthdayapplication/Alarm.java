package com.example.william.earthdayapplication;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import static android.R.id.message;

public class Alarm extends AppCompatActivity {

    private NumberPicker picker;
    private Button button;
    private TextView text;
    private int data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        picker = (NumberPicker)findViewById(R.id.numberPicker1);
        button = (Button) findViewById(R.id.button1);
        text = (TextView) findViewById(R.id.textView);

        picker.setMaxValue(24);
        picker.setMinValue(0);
        picker.setWrapSelectorWheel(false);

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {

                data = newVal;

            }
        });

    }

    public void setAlarm(View view){
        createAlarm("Water your plants!", data, 0);
    }

    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                ;
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
