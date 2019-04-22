package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {
    private Toolbar appBar;
    private Button continueButton;
    private Button googleButton;
    private Button facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        continueButton = (Button) findViewById(R.id.button9);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });

        googleButton = (Button) findViewById(R.id.button8);
        googleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });

        facebookButton = (Button) findViewById(R.id.button6);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
    }

    public void registerNext() {
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, Tabs.class);

        // Start the new activity.
        startActivity(loginIntent);

    }
}
