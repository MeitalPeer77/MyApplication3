package com.example.RunningMatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProfileGallery extends AppCompatActivity {

    private Button homePageButton;
    private Button partnersButton;
    private Button profileButton;
    private Button contactButton;
    private Button backbutton;
    private Button removeButton;

    private String imageUrl;
    private String name;
    private String distance;
    private String location;
    private String pace;
    private ArrayList<String> goals;
    private ArrayList<String> events;
    private String info;
    private String email;

    FirebaseFirestore fireStoreDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireStoreDatabase = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_profile_others_gallery);

        homePageButton = (Button) findViewById(R.id.action_bar_homepage_2);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();

            }

        });

        removeButton = (Button) findViewById(R.id.delete_partner);

        backbutton = (Button) findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();

            }
        });

        profileButton = (Button) findViewById(R.id.actio_bar_events_2);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();

            }

        });
        partnersButton = (Button) findViewById(R.id.action_bar_partners_2);

        if (RunningMatchHomePage.showPartnerNotofication){
            partnersButton.setBackgroundResource(R.drawable.partner_notification_color);
        }
        else{partnersButton.setBackgroundResource(R.drawable.partner_icon_color);}

        partnersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();
            }
        });

        final String phoneNumber = getIntent().getStringExtra("phone");
        contactButton = (Button)findViewById(R.id.update);
        contactButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(Intent.ACTION_DIAL);
                                                 intent.setData(Uri.parse("tel:" + phoneNumber));
                                                 startActivity(intent);
                                             }
                                         });
        getIncomingIntent();

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> not4meArray = RunningMatchHomePage.currentUser.getNot4me();
                not4meArray.add(email);
                RunningMatchHomePage.currentUser.setNot4me(not4meArray);
                fireStoreDatabase.collection("users").document(RunningMatchHomePage.currentUser.getEmail().
                        replace(".", "")).update("not4me", not4meArray);

                ArrayList<String> matches = RunningMatchHomePage.currentUser.getMatches();
                matches.remove(email);

                fireStoreDatabase.collection("users").document(RunningMatchHomePage.currentUser.getEmail().
                        replace(".", "")).update("matches", matches);

                ArrayList<String> myLikes = RunningMatchHomePage.currentUser.getMyLikesArray();
                myLikes.remove(email);

                fireStoreDatabase.collection("users").document(RunningMatchHomePage.currentUser.getEmail().
                        replace(".", "")).update("myLikesArray", myLikes);

                partners();
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("image") && getIntent().hasExtra("Profile name") && getIntent().hasExtra("distances") && getIntent().hasExtra("location") && getIntent().hasExtra("pace") && getIntent().hasExtra("goals")){
            imageUrl = getIntent().getStringExtra("image");
            name = getIntent().getStringExtra("Profile name");
            distance = getIntent().getStringExtra("km");
            location = getIntent().getStringExtra("location");
            pace = getIntent().getStringExtra("pace");
            goals =  getIntent().getStringArrayListExtra("goals");
            events =  getIntent().getStringArrayListExtra("events");
            info = getIntent().getStringExtra("info");
            email = getIntent().getStringExtra("email");

            setProfileContent(imageUrl, name, distance, location, pace, info, goals, events);
        }
    }
    private void setProfileContent(String imageUrl, String profileName, String profileDistance, String profileLocation, String profilePace, String profileInfo, ArrayList<String> goals, ArrayList<String> events){
        TextView name = findViewById(R.id.profile_other_name);
        name.setText(profileName);

        TextView info = findViewById(R.id.profile_other_info);
        info.setText(profileInfo);

        TextView distance = findViewById(R.id.prifile_other_distances_input);
        DecimalFormat df = new DecimalFormat("#.#");
        distance.setText(df.format(profileDistance));
        
        TextView pace = findViewById(R.id.profile_other_pace_input);
        pace.setText(profilePace);

        TextView location = findViewById(R.id.profile_other_location_input);
        location.setText(profileLocation);

        ImageView image = findViewById(R.id.profile_other_image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);

        RecyclerView goalsAdapter = findViewById(R.id.goals_adapter_others_profile);
        GoalsAdapter adapter = new GoalsAdapter(this, goals);
        goalsAdapter.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        goalsAdapter.setLayoutManager(layoutManager);

        RecyclerView eventAdapter = findViewById(R.id.events_adapter_others_profile);
        EventAdapter adapter2 = new EventAdapter(this, events);
        eventAdapter.setAdapter(adapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        eventAdapter.setLayoutManager(layoutManager2);

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

        partnersIntent.putExtra("user", RunningMatchHomePage.currentUser);
        partnersIntent.putExtra("usersMap", RunningMatchHomePage.usersMap);
        partnersIntent.putExtra("userMatches", RunningMatchHomePage.currentUser.getMatches());

        // Start the new activity.
        startActivity(partnersIntent);

    }
}
