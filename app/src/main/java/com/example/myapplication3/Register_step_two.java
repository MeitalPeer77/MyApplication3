package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Register_step_two extends AppCompatActivity {
    String[] RUNlIST = {"1 km", "2 km", "4 km", "6 km", "8 km", "10 km"};
    private Button nextbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_two);

        ArrayAdapter<String> arrayAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, RUNlIST);
        arrayAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spiner = (Spinner)findViewById(R.id.spinner);
        spiner.setAdapter(arrayAdap);

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
