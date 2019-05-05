package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Register_step_two extends AppCompatActivity {
    String[] kmArray = {"1", "2", "3", "4","5", "6","7", "8","9", "10", "11", "12", "13", "14", "15"};
    String [] minArray = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60"};

    private Button nextbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_two);

        ArrayAdapter<String> arrayaddapterKm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kmArray);
        arrayaddapterKm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerKm = (Spinner)findViewById(R.id.spinner_km);
        spinnerKm.setAdapter(arrayaddapterKm);

        ArrayAdapter<String> arrayaddapterMin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, minArray);
        arrayaddapterMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerMin = (Spinner)findViewById(R.id.spinner_min);
        spinnerMin.setAdapter(arrayaddapterMin);

        nextbutton = (Button) findViewById(R.id.done_register);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
    }

    public void registerNext() {
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, Location.class);

        // Start the new activity.
        startActivity(loginIntent);

    }


}
