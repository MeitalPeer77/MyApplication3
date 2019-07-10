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
        setContentView(R.layout.group_tab);



        mAuth = FirebaseAuth.getInstance();
        fireStoreDatabase = FirebaseFirestore.getInstance();

        mDataBase = FirebaseDatabase.getInstance().getReference();
        extras = getIntent().getExtras();
        User s = RunningMatchHomePage.currentUser;
        currentUser =(User) getIntent().getSerializableExtra("user");
        currentUserMatches =(ArrayList<String>) getIntent().getSerializableExtra("userMatches");
        usersMap = (HashMap<String, User>) getIntent().getSerializableExtra("usersMap");


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
        //Todo: add from firebase, do it in a loop
//        //mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/runningmatch-f9e90.appspot.com/o/Photos%2F6433?alt=media&token=2deb2ceb-13bc-4d0b-8969-5640ea8530af");
//        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/10570528_663942363682968_3472551542158599282_n.jpg?_nc_cat=108&_nc_ht=scontent.ftlv6-1.fna&oh=4b5e98824fb8295aad9b55de97be83be&oe=5D6910B9");
//        mNames.add("Nathaniel Tavisal");
//        mDistance.add("5");
//        mPace.add("35");
//        mLocations.add("0.7");
//
//
//        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/314570_2286081785515_743884461_n.jpg?_nc_cat=108&_nc_ht=scontent.ftlv6-1.fna&oh=72538202dcbfa0c5da9f3f8aeb3b8435&oe=5D75FC76");
//        mNames.add("Meital Peer");
//        mDistance.add("5");
//        mPace.add("30");
//        mLocations.add("1.3");
//
//        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t31.0-8/21122579_1833916359957237_6719538012298659073_o.jpg?_nc_cat=109&_nc_ht=scontent.ftlv6-1.fna&oh=b0fd68b0be099e148062a31a3d20a348&oe=5D2AC9E7");
//        mNames.add("Shira Weitman");
//        mDistance.add("6");
//        mPace.add("35");
//        mLocations.add("1.8");
//
//
//        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/559079_10151809848077388_1166275349_n.jpg?_nc_cat=102&_nc_ht=scontent.ftlv6-1.fna&oh=7aade7d7463f3d5ed16c32c2f30562ef&oe=5D326041");
//        mNames.add("Michal Gordon");
//        mDistance.add("4");
//        mPace.add("25");
//        mLocations.add("1.7");
//
//
//        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/10898096_10152956054335912_5153974417356391101_n.jpg?_nc_cat=109&_nc_ht=scontent.ftlv6-1.fna&oh=f131a44166cc0a4d57b4737366da0a16&oe=5D6512E9");
//        mNames.add("Liav Alter");
//        mDistance.add("4");
//        mPace.add("25");
//        mLocations.add("1.9");
//

        setUserMatches();
        initRecycleView();
    }

    private void setUserMatches() {
        if (currentUserMatches != null) {
            for (String mail : currentUserMatches) {
                User cur = usersMap.get(mail);
                if (cur != null) {
                    mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/559079_10151809848077388_1166275349_n.jpg?_nc_cat=102&_nc_ht=scontent.ftlv6-1.fna&oh=7aade7d7463f3d5ed16c32c2f30562ef&oe=5D326041");
                    double distance = CalculateRate.distance(Double.parseDouble(currentUser.getLatitude()),
                            Double.parseDouble(currentUser.getLongitude()),
                            Double.parseDouble(cur.getLatitude()),
                            Double.parseDouble(cur.getLongitude()));
                    mNames.add(cur.getUserName());
                    mDistance.add(cur.getDistanceRangeFromUser());
                    mPace.add(cur.getTime());
                    mLocations.add(Double.toString(distance));
                    mInfo.add(cur.getUserDescription());

                }
            }
        }

    }

    /**
     * Initialize the recycle view
     */
    private void initRecycleView(){
        RecyclerView recycleView = findViewById(R.id.recycler_view);
        PartnersRecyclerViewAdapter adapter = new PartnersRecyclerViewAdapter(this, mNames, mImageUrls, mLocations, mDistance, mPace, mInfo);
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

    //Todo: use it to get the past matches!


}


