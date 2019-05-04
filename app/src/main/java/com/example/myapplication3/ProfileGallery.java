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
        if(getIntent().hasExtra("image") && getIntent().hasExtra("profile name") && getIntent().hasExtra("location") && getIntent().hasExtra("pace") && getIntent().hasExtra("info")){
            String imageUrl = getIntent().getStringExtra("image");
            String name = getIntent().getStringExtra("profile name");
//            String distance = getIntent().getStringExtra("distance");
            String location = getIntent().getStringExtra("location");
            String pace = getIntent().getStringExtra("pace");
            String info = getIntent().getStringExtra("info");


            setProfileContent(imageUrl, name, location, pace, info);
        }
    }
    private void setProfileContent(String imageUrl, String profileName, String profilePace, String profileLocation, String profileInfo){
        TextView name = findViewById(R.id.profile_other_name);
        name.setText(profileName);

//        TextView distance = findViewById(R.id.prifile_other_distances_input);
//        distance.setText(profileDistance);

        TextView pace = findViewById(R.id.profile_other_pace_input);
        pace.setText(profilePace);

        TextView location = findViewById(R.id.profile_other_location_input);
        location.setText(profileLocation);

        TextView info = findViewById(R.id.profile_other_info);
        info.setText(profileInfo);

        ImageView image = findViewById(R.id.profile_other_image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);

    }
}
