package com.kaka.weatherapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    Button register,checkPin;
    TextInputEditText pincode,dob,phNum,name,add1,add2;

    TextView district, state;
    final Calendar myCalendar = Calendar.getInstance();

    String[] chooseGender = { "Male", "Female"};
    String gender="Male", info="";

    SharedPreferences sharedPreferences;

    public static final String mypref="mypref";
    public static final String Number="number";
    public static final String Name="name";
    public static final String Gender="gender";
    public static final String DOB="dob";
    public static final String Add1="add1";
    public static final String Add2="add2";
    public static final String Pincode="pincode";
    public static final String State="state";
    public static final String infoSaved="infoSaved";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = findViewById(R.id.register);
        pincode = findViewById(R.id.pincode);
        checkPin = findViewById(R.id.checkPin);
        district = findViewById(R.id.district);
        state = findViewById(R.id.state);
        dob = findViewById(R.id.dob);
        phNum = findViewById(R.id.mobileNum);
        name = findViewById(R.id.name);
        add1 = findViewById(R.id.add);
        add2 = findViewById(R.id.add2);

        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        checkPin.setEnabled(false);
        checkPin.setBackgroundColor(0xFFd3d3d3);
        checkPin.setTextColor(0xFFffffff);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (dob.getRight() - dob.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        System.out.println("clicked");
                        new DatePickerDialog(MainActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        return true;
                    }
                }
                return false;
            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,chooseGender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#505050"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Register");

        sharedPreferences=getSharedPreferences(mypref, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(infoSaved)){
            Intent intent = new Intent(MainActivity.this, WeatherToday.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Logged In Successfully",Toast.LENGTH_SHORT).show();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pincode.getText().toString().length()==0 && name.getText().toString().length()==0 && dob.getText().toString().length()==0 &&
                        phNum.getText().toString().length()==0 && add1.getText().toString().length()==0){
                    pincode.setError("Field must not be empty!");
                    name.setError("Field must not be empty!");
                    dob.setError("Field must not be empty!");
                    add1.setError("Field must not be empty!");
                    phNum.setError("Field must not be empty!");

                }else if(phNum.getText().toString().length()<10){
                    phNum.setError("Enter a valid Mobile Number!");
                }else if(add1.getText().toString().length()<3){
                    add1.setError("Minimum 3 characters required!");
                }else if(district.getText().toString().equals("District")  || district.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(),"First Check Your Pin Code!!",Toast.LENGTH_SHORT).show();
                }else{
                    info="saved";
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Number,phNum.getText().toString());
                    editor.putString(Name,name.getText().toString());
                    editor.putString(Gender,gender);
                    editor.putString(DOB,dob.getText().toString());
                    editor.putString(Add1,add1.getText().toString());
                    editor.putString(Add2,add2.getText().toString());
                    editor.putString(State,state.getText().toString());
                    editor.putString(infoSaved,info);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, WeatherToday.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Logged In Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }



//                Intent intent = new Intent(MainActivity.this, WeatherToday.class);
//                startActivity(intent);
            }
        });


        pincode.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    checkPin.setEnabled(false);
                    checkPin.setBackgroundColor(0xFFC0C0C0);
                    checkPin.setTextColor(0xFFffffff);

                } else {
                    checkPin.setEnabled(true);
                    checkPin.setBackgroundColor(0xFF505050);
                    checkPin.setTextColor(0xFFffffff);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        checkPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ProgressDialog proDialog = ProgressDialog.show(getApplicationContext(), "title", "message");
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://api.postalpincode.in/pincode/"+pincode.getText().toString();


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try{
                                    /*district.setText(response.substring(response.indexOf("District")+11
                                            , response.indexOf("\",\"Division")));

                                    state.setText(response.substring(response.indexOf("State")+8
                                            , response.indexOf("\",\"Country")));*/

                                    JSONArray jsonarray = new JSONArray(response);
                                    for (int i = 0, len=jsonarray.length(); i < len; i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        //                                         districtt = jsonobject.getString("Message");
//                                         statee = jsonobject.getString("Status");
                                        JSONArray jsonArray=jsonobject.optJSONArray("PostOffice");
                                        for(int j=0;j<jsonArray.length();j++){
                                            JSONObject childobject=jsonArray.getJSONObject(i);

                                            district.setText(childobject.optString("District"));
                                            state.setText(childobject.optString("State"));
                                            //proDialog.cancel();
                                        }
                                    }

                                } catch (Exception e) {
                                    System.out.println("Enter valid Pin Code");
                                    Toast.makeText(getApplicationContext(),"Enter valid Pin Code",Toast.LENGTH_SHORT).show();
                                    district.setText("");
                                    state.setText("");
                                    //proDialog.cancel();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                        Toast.makeText(getApplicationContext(),"Network Error, Try Again!!",Toast.LENGTH_SHORT).show();
                        //proDialog.cancel();
                    }
                });

                queue.add(stringRequest);



            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = chooseGender[position];
        //Toast.makeText(getApplicationContext(),chooseGender[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}