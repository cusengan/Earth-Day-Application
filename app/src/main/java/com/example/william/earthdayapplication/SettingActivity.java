package com.example.william.earthdayapplication;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private Switch VibrateSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        VibrateSwitch = (Switch) findViewById(R.id.VibrateSwitch);
        VibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
        public void Vibration(View v) {
            Vibrator VibrateSwitch = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            VibrateSwitch.vibrate(300);
        }
}


