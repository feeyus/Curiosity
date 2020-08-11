package com.example.curiosity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class HealthWeight extends AppCompatActivity {

    ImageButton back_button;
    ImageButton settings_button;
    Button weightButton;
    Button activityButton;
    Button appetiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_weight);

        back_button=(ImageButton)findViewById(R.id.back_button);
        settings_button=(ImageButton)findViewById(R.id.settings_button);
        weightButton = (Button)findViewById(R.id.weightButton);
        activityButton=(Button)findViewById(R.id.activityButton);
        appetiteButton=(Button)findViewById(R.id.appetiteButton);

        //on press settings button
        settings_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(HealthWeight.this,Settings.class);
                startActivity(intent);
            }
        });

        //on press go back
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(HealthWeight.this,HealthRecords.class);
                startActivity(intent);
            }
        });

        //track weight button
        weightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(HealthWeight.this,TrackWeight.class);
                startActivity(intent);
            }
        });
//
        //log activity level button
        activityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(HealthWeight.this,ActivityLevel.class);
                startActivity(intent);
            }
        });
//
        //monitor pet appetite
        appetiteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(HealthWeight.this,MonitorAppetite.class);
                startActivity(intent);
            }
        });


        //on load display date
        Calendar calendar= Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView today=findViewById(R.id.displayDate);
        today.setText(currentDate);

    }
}