package com.example.myapplication3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {
    String[] RUNlIST = {"1 km", "2 km", "4 km", "6 km", "8 km", "10 km"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ArrayAdapter<String> arrayAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, RUNlIST);
        arrayAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spiner = (Spinner)findViewById(R.id.spinner);
        spiner.setAdapter(arrayAdap);
    }
}
