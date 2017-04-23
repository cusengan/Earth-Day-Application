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
import java.util.Random;

public class SettingActivity extends AppCompatActivity {
    private int twiceChecker;

    Button button;
    TextView factView;
    String facts[] = {"Earth Day was first celebrated in April 1970" ,
        "The Date of Earth Day was specifically for mobilizing college students" ,
        "Internationally, Earth Day is refer as Mother Earth Day<3" ,
        "Over 100,000 people in China rode their bikes on Earth Day 2012" ,
        "In Panama, they planted 100 species of endangered orchids to prevent their extincion in honor of Earth Day" ,
        "The Pafuri baobab tree is over 2000 years old" ,
        "Over 200 million people participated in 1990" ,
        "Garbages in landfills can last up to 30 years" ,
        "An average person throw 4 lb of trash everyday" ,
        "One-Third of all water is used for toilet" ,
        "5 billion aluminum cans are used each year" ,
        "A typical computer monitor contains about 4 pounds of lead" ,
        "50-70 gallons of water for 10 minute shower" ,
        "Plastics take 500 years to break down" ,
        "It take glass about 1 million years for a glass bottle to break down in a land fill" ,
        "Earth hour was set up to raise awareness on climate change",
            "According to recent Gallup polls, 42 percent of Americans believe that the dangers of climate change are exaggerated"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        button = (Button) findViewById(R.id.buttonFacts);
        factView = (TextView) findViewById(R.id.factText);

    }

    public void generateFact(View view){
        Random rand = new Random();
        twiceChecker += 1;
        if (twiceChecker == 1){
            Toast.makeText(getApplicationContext(), "There's more fact where that came from", Toast.LENGTH_LONG).show();
        }
        if (twiceChecker == 2){
            Toast.makeText(getApplicationContext(), "Unfortunately, there's no tree points here", Toast.LENGTH_LONG).show();
        }
        int n = rand.nextInt(facts.length);
        factView.setText(facts[n]);

    }

}


