package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PersonalDetails extends AppCompatActivity {
    String[] RUNlIST = {"1 km", "2 km", "4 km", "6 km", "8 km", "10 km"};
    private Button nextbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);

        ArrayAdapter<String> arrayAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, RUNlIST);
        arrayAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spiner = (Spinner)findViewById(R.id.spinner_km);
        spiner.setAdapter(arrayAdap);

        nextbutton = (Button) findViewById(R.id.done_register);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToProflie();
            }
        });
    }

    public void backToProflie() {
        // Create an Intent to start the second activity
        Intent profileIntent = new Intent(this, profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }


}
