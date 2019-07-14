package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the matched running partners screen
 */
public class PartnersList extends AppCompatActivity implements Serializable {

    //******************  Buttons and fields ****************//

                      //  Action Bar Buttons //
    /* A button that navigated to the matched partners screen */
    private Button matchButton;

    /* A button that navigates to the Profile screen of the user */
    private Button profileButton;

    /* A button that navigated to the events screen */
    private Button eventButton;

    HashMap<String, User> usersMap;
                     // Partners information //
    /* The names of the matched partners */
    private ArrayList<String> mNames = new ArrayList<>();

    /* The Images of the matched partners */
    private ArrayList<String> mImageUrls = new ArrayList<>();

    /* The Distances of the matched partners */
    private ArrayList<String> mDistance = new ArrayList<>();


    /* The paces of the matched partners */
    private ArrayList<String> mPace = new ArrayList<>();

    /* The Locations of the matched partners */
    private ArrayList<String> mLocations = new ArrayList<>();

    private ArrayList<String> mInfo = new ArrayList<>();

    private ArrayList<String> mPhones = new ArrayList<>();


    private ArrayList<String> mEmails = new ArrayList<>();

    private ArrayList<ArrayList<String>> mGoals = new ArrayList<>();

    private ArrayList<ArrayList<String>> mEvents = new ArrayList<>();

    /* A list of all the matched users */
    public ArrayList<User> matchesArray;

    private String lat;
    private String lon;

    private User currentUser;

    FirebaseFirestore fireStoreDatabase;

    Bundle extras;

    ArrayList<User> usersArray;

    ArrayList<String> userMatches;


    //******************  Firebase Objects ****************//

    /* Represents the database */
    private DatabaseReference mDataBase;

    /* The authentication object of the app */
    private FirebaseAuth mAuth;
    private String currentUserEmail;
    private ArrayList<String> currentUserMatches;


    /**
     * Creates the buttons and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (RunningMatchHomePage.showPartnerNotofication) {
            RunningMatchHomePage.showPartnerNotofication = false;
        }
        setContentView(R.layout.group_tab);


        //TODO: make it pink
        matchButton = (Button) findViewById(R.id.action_bar_matches);
        matchButton.setBackgroundResource(R.drawable.partner_icon_color);


        mAuth = FirebaseAuth.getInstance();
        fireStoreDatabase = FirebaseFirestore.getInstance();

        mDataBase = FirebaseDatabase.getInstance().getReference();
        extras = getIntent().getExtras();
        User s = RunningMatchHomePage.currentUser;
        currentUser =(User) getIntent().getSerializableExtra("user");
        currentUserMatches =(ArrayList<String>) getIntent().getSerializableExtra("userMatches");
        usersMap = RunningMatchHomePage.usersMap;


        currentUserEmail = mAuth.getCurrentUser().getEmail();
        currentUserEmail = currentUserEmail.replace(".", "");


//        getMatches();
        initImageBitMap();

        eventButton = (Button)findViewById(R.id.action_bar_event);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event();
            }
        });

        matchButton = (Button) findViewById(R.id.action_bar_homepage);
        matchButton.setOnClickListener(new View.OnClickListener() {
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

    }

    /**
     * Add the images of he matched partners
     */
    private void initImageBitMap(){
        setUserMatches();
        initRecycleView();
    }

    private void setUserMatches() {
        if (currentUserMatches != null) {
            for (String mail : currentUserMatches) {
                User cur = usersMap.get(mail);
                if (cur != null) {
                    mImageUrls.add(cur.getProfilePic());
                    double distance = CalculateRate.distance(Double.parseDouble(currentUser.getLatitude()),
                            Double.parseDouble(currentUser.getLongitude()),
                            Double.parseDouble(cur.getLatitude()),
                            Double.parseDouble(cur.getLongitude()));
                    mNames.add(cur.getUserName());
                    mDistance.add(cur.getDistanceRangeFromUser());
                    mPace.add(cur.getTime());
                    mLocations.add(Double.toString(distance));
                    mGoals.add(cur.getGoals());
                    mEvents.add(cur.getEvents());
                    mInfo.add(cur.getUserDescription());
                    mPhones.add(cur.getPhoneNumber());
                    mEmails.add(cur.getEmail());

                }
            }
        }

    }

    /**
     * Initialize the recycle view
     */
    private void initRecycleView(){
        RecyclerView recycleView = findViewById(R.id.recycler_view);
        PartnersRecyclerViewAdapter adapter = new PartnersRecyclerViewAdapter(this, mNames, mImageUrls, mLocations, mInfo, mDistance, mPace, mGoals, mEvents, mPhones,mEmails);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Transfer to the Profile screen
     */
    public void profile() {
        // Create an Intent to start the  activity
        Intent profileIntent = new Intent(this, Profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }

    /**
     * Transfer to the suggestions page
     */
    public void suggestions() {
        // Create an Intent to start the  activity
        Intent suggestiosIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }

    /**
     * Transfer to the events page
     */
    public void event() {
        // Create an Intent to start the activity
        Intent eventIntent = new Intent(this, EventActivity.class);

        // Start the new activity.
        startActivity(eventIntent);
    }

}


