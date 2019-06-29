package com.example.RunningMatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Represents the events screen
 */
public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity";

    /* A list of the names of the events */
    private ArrayList<String> mNames = new ArrayList<>();

    /* A list of the images of the events */
    private ArrayList<String> mImagesURI = new ArrayList<>();

    /* A list of the details of the events */
    private ArrayList<String> mEventDetails = new ArrayList<>();

    /* A list of the sign uo urls of the events */
    private ArrayList<Integer> mSighUp = new ArrayList<>();

    //******************  Buttons and fields ****************//

                      //  Action Bar Buttons //
    /* A button that navigated to the home page screen */
    private Button homePageButton;

    /* A button that navigated to the matched partners screen */
    private Button partnersButton;

    /* A button that navigated to the Profile screen */
    private Button profileButton;

    /**
     * Creates the buttons and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_acticity);

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

        profileButton = (Button) findViewById(R.id.action_bar_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });

        Log.d(TAG, "onCreate: started.");

        initImageBotmap();
    }

    /**
     * Initialize the events information
     */
    private void initImageBotmap() {
        Log.d(TAG, "initImageBotmap: prapering bitmap");

        mImagesURI.add("http://www.winning.co.il/events/2019/golan/header.png");
        mNames.add("Golan Race");
        mEventDetails.add("Majdal Shams, 01.06.19");
        mSighUp.add(R.string.golan_link);

        mImagesURI.add("http://www.winning.co.il/events/2019/kiryat-gat/header.png");
        mNames.add("Night Run");
        mEventDetails.add("Kiryat Gat, 06.06.19");
        mSighUp.add(R.string.kiryat_gat_link);

        mImagesURI.add("http://liga.org.il/wp-content/uploads/2017/12/liga-logo-200.jpg");
        mNames.add("Running in Work");
        mEventDetails.add("Herzliya, 14.06.19");
        mSighUp.add(R.string.work_link);

        mImagesURI.add("https://www.shvoong.co.il/wp-content/uploads/2017/05/unnamed.jpg");
        mNames.add("Running With Shahar");
        mEventDetails.add("Alonei Habashan, 14.06.19");
        mSighUp.add(R.string.shahar_link);

        mImagesURI.add("https://aradmasadarun.co.il/wp-content/uploads/2019/04/arad-masada-cover-event.png");
        mNames.add("Half Marathon Arad Masada");
        mEventDetails.add("Arad, 27.06.19");
        mSighUp.add(R.string.arad_link);

        mImagesURI.add("https://www.shvoong.co.il/wp-content/uploads/2017/08/Nahal_july.png");
        mNames.add("Nachal Race");
        mEventDetails.add("Tel Aviv, 12.07.19");
        mSighUp.add(R.string.nachal_link);

        initRecyclerView();

    }

    /**
     * initialize the recycle view
     */
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView");
        RecyclerView recyclerViewAdapter = findViewById(R.id.event_recycler);
        EventRecyclerAdapter adapter = new EventRecyclerAdapter(this, mNames, mImagesURI,
                mEventDetails, mSighUp);
        recyclerViewAdapter.setAdapter(adapter);
        recyclerViewAdapter.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * Transfer to home page screen
     */
    public void suggestions() {
        // Create an Intent to start the  activity
        Intent suggestiosIntent = new Intent(this, RunningMatchHomePage.class);
        // Start the new activity.
        startActivity(suggestiosIntent);
    }

    /**
     * Transfer to partners screen
     */
    public void partners() {
        // Create an Intent to start the second activity
        Intent partnersIntent = new Intent(this, PartnersList.class);
        // Start the new activity.
        startActivity(partnersIntent);
    }

    /**
     * transfer to Profile screen
     */
    public void profile() {
        // Create an Intent to start the  activity
        Intent profileIntent = new Intent(this, Profile.class);
        // Start the new activity.
        startActivity(profileIntent);
    }
}
