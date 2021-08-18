package com.kaka.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import static com.kaka.weatherapp.MainActivity.infoSaved;
import static com.kaka.weatherapp.MainActivity.mypref;

public class WeatherToday extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button showResult;
    TextView Centigrade, Fahrenheit, Latitude, Longitude;
    TextInputEditText cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_today);

        showResult = findViewById(R.id.showResult);
        Centigrade = findViewById(R.id.centigrade);
        Fahrenheit = findViewById(R.id.fahrenheit);
        Latitude = findViewById(R.id.lat);
        Longitude = findViewById(R.id.longi);
        cityName = findViewById(R.id.cityName);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#505050"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Weather Today");

        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://api.weatherapi.com/v1/current.json?key=35c9f92ac5bf4df0811144140212307&q="+ cityName.getText().toString() + "&aqi=no";


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    /*district.setText(response.substring(response.indexOf("District")+11
                                            , response.indexOf("\",\"Division")));

                                    state.setText(response.substring(response.indexOf("State")+8
                                            , response.indexOf("\",\"Country")));*/

                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject location = jsonObject.getJSONObject("location");
                                    JSONObject current = jsonObject.getJSONObject("current");

                                    Centigrade.setText(current.getString("temp_c")+" °C");
                                    Fahrenheit.setText(current.getString("temp_f")+ " °F");

                                    Latitude.setText(location.getString("lat")+" Latitude");
                                    Longitude.setText(location.getString("lon")+ " Longitude");


                                } catch (Exception e) {
                                    System.out.println("Enter valid Pin Code" + e);
                                    Toast.makeText(getApplicationContext(), "Enter valid Pin Code", Toast.LENGTH_SHORT).show();
                                    Centigrade.setText("");
                                    Fahrenheit.setText("");
                                    //proDialog.cancel();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                        Toast.makeText(getApplicationContext(), "Network Error, Try Again!!", Toast.LENGTH_SHORT).show();
                        //proDialog.cancel();
                    }
                });

                queue.add(stringRequest);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.myProfile) {
            // do something
            Intent intent = new Intent(WeatherToday.this, MyProfile.class);
            startActivity(intent);
        }
        if (id == R.id.logout) {
            sharedPreferences = getSharedPreferences(mypref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(infoSaved, null);
            editor.commit();
            Intent intent = new Intent(WeatherToday.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}