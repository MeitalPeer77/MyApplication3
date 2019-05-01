package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class profile extends AppCompatActivity {
    private Button homePageButton;
    private Button partnersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        homePageButton = (Button) findViewById(R.id.action_bar_homepage);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();

            }

        });
        partnersButton = (Button) findViewById(R.id.action_bar_matches);
        partnersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();

            }

        });
    }

    public void btnSetting_onClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void suggestions() {
        // Create an Intent to start the second activity
        Intent suggestiosIntent = new Intent(this, RunningMatch.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }
    public void partners() {
        // Create an Intent to start the second activity
        Intent partnersIntent = new Intent(this, partners_list.class);

        // Start the new activity.
        startActivity(partnersIntent);

    }

}
