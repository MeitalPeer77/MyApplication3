package com.example.RunningMatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileGallery extends AppCompatActivity {

    private Button homePageButton;
    private Button partnersButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_others_gallery);

        homePageButton = (Button) findViewById(R.id.action_bar_homepage);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();

            }

        });

        profileButton = (Button) findViewById(R.id.action_bar_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();

            }

        });
        partnersButton = (Button) findViewById(R.id.action_bar_matches);
        partnersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();

            }


        });
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("image") && getIntent().hasExtra("Profile name") && getIntent().hasExtra("distances") && getIntent().hasExtra("location") && getIntent().hasExtra("pace") && getIntent().hasExtra("goals")){
            String imageUrl = getIntent().getStringExtra("image");
            String name = getIntent().getStringExtra("Profile name");
            String distance = getIntent().getStringExtra("distances");
            String location = getIntent().getStringExtra("location");
            String pace = getIntent().getStringExtra("pace");
            ArrayList<String> goals =  getIntent().getStringArrayListExtra("goals");

            setProfileContent(imageUrl, name, distance, location, pace, goals);
        }
    }
    private void setProfileContent(String imageUrl, String profileName, String profileDistance, String profileLocation, String profilePace, ArrayList<String> goals){
        TextView name = findViewById(R.id.profile_other_name);
        name.setText(profileName);

        TextView distance = findViewById(R.id.prifile_other_distances_input);
        distance.setText(profileDistance);

        TextView pace = findViewById(R.id.profile_other_pace_input);
        pace.setText(profilePace);

        TextView location = findViewById(R.id.profile_other_location_input);
        location.setText(profileLocation);

        ImageView image = findViewById(R.id.profile_other_image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);

        //TODO after updating activity to work with user pbjects, enable this and update
        RecyclerView goalsAdapter = findViewById(R.id.goals_adapter_others_profile);
        GoalsAdapter adapter = new GoalsAdapter(this, goals);
        goalsAdapter.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        goalsAdapter.setLayoutManager(layoutManager);


    }

    public void suggestions() {
        // Create an Intent to start the second activity
        Intent suggestiosIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }

    public void profile() {
        // Create an Intent to start the second activity
        Intent profileIntent = new Intent(this, Profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }
    public void partners() {
        // Create an Intent to start the second activity
        Intent partnersIntent = new Intent(this, PartnersList.class);

        // Start the new activity.
        startActivity(partnersIntent);

    }
}
