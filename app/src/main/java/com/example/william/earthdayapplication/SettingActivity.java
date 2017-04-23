package com.example.william.earthdayapplication;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button button;
    TextView factView;
    String facts[] = {"According to recent Gallup polls, 42 percent of Americans believe that the dangers of climate change are exaggerated"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        button = (Button) findViewById(R.id.buttonFacts);
        factView = (TextView) findViewById(R.id.factText);

    }

    public void generateFact(View view){
        factView.setText(facts[0]);

    }

}


