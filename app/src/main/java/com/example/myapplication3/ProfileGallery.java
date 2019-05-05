package com.example.myapplication3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProfileGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_others_gallery);
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("image") && getIntent().hasExtra("profile name") && getIntent().hasExtra("distance") && getIntent().hasExtra("location") && getIntent().hasExtra("pace")){
            String imageUrl = getIntent().getStringExtra("image");
            String name = getIntent().getStringExtra("profile name");
            String distance = getIntent().getStringExtra("distances");
            String location = getIntent().getStringExtra("location");
            String pace = getIntent().getStringExtra("pace");

            setProfileContent(imageUrl, name, distance, location, pace);
        }
    }
    private void setProfileContent(String imageUrl, String profileName, String profileDistance, String profileLocation, String profilePace){
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

    }
}
