package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Location extends AppCompatActivity {

    public Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        nextButton = (Button) findViewById(R.id.lets_run);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();

            }

        });

    }

    public void suggestions() {
        // Create an Intent to start the second activity
        Intent suggestiosIntent = new Intent(this, RunningMatch.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }

}
