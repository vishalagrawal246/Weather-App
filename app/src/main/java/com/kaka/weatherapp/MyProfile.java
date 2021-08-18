package com.kaka.weatherapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import static com.kaka.weatherapp.MainActivity.Add1;
import static com.kaka.weatherapp.MainActivity.DOB;
import static com.kaka.weatherapp.MainActivity.Gender;
import static com.kaka.weatherapp.MainActivity.Name;
import static com.kaka.weatherapp.MainActivity.Pincode;
import static com.kaka.weatherapp.MainActivity.State;
import static com.kaka.weatherapp.MainActivity.mypref;

public class MyProfile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView name,sexAndDOB,add,stateAndPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        name = findViewById(R.id.myName);
        sexAndDOB = findViewById(R.id.mysexAndDOB);
        add = findViewById(R.id.myAdd);
        stateAndPin = findViewById(R.id.myStateAndPin);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#505050"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("My Profile");



        sharedPreferences=getSharedPreferences(mypref, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(Name)){
            name.setText(sharedPreferences.getString(Name,""));
        }

        if(sharedPreferences.contains(Gender)){
            sexAndDOB.setText(sharedPreferences.getString(Gender,"") +", "+ sharedPreferences.getString(DOB,""));
        }

        if(sharedPreferences.contains(Add1)){
            add.setText(sharedPreferences.getString(Add1,""));
        }

        if(sharedPreferences.contains(Pincode)){
            stateAndPin.setText(sharedPreferences.getString(State,"") +", "+ sharedPreferences.getString(Pincode,""));
        }
    }
}