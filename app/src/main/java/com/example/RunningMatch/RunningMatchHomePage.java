package com.example.RunningMatch;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RunningMatchHomePage extends AppCompatActivity {
    private Toolbar myToolBar;
    private Button profileButton;
    private Button homepageButton;
    private Button matchButton;
    private Button popupButton;


    // card slide suggestions
    private ViewPager viewPager;
    private SlideAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_tab);




        profileButton = (Button) findViewById(R.id.action_bar_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });
//;
        matchButton = (Button) findViewById(R.id.action_bar_matches);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();

            }

        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);

        popupButton = (Button)findViewById(R.id.lets_run);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RunningMatchHomePage.this, pop.class));
            }
        });



    }

    public void profile() {
        // Create an Intent to start the second activity
        Intent profileIntent = new Intent(this, profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }

    public void partners() {
        // Create an Intent to start the second activity
        Intent partnersIntent = new Intent(this, partners_list.class);

        // Start the new activity.
        startActivity(partnersIntent);

    }




}