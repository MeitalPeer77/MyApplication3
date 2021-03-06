package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the events screen
 */
public class EventActivity extends AppCompatActivity implements Serializable {

    private static final String TAG = "EventActivity";

    /* A list of the names of the events */
    private ArrayList<String> mNames = new ArrayList<>();

    /* A list of the images of the events */
    private ArrayList<String> mImagesURI = new ArrayList<>();

    /* currenet user */
    private User currentUser;

    /* current user matches*/
    private ArrayList<String> currentUserMatches;

    /* Integers represents the number of participants in each event */
    private Integer GOLAN_RACE_COUNTER = 0;
    private Integer NIGHT_RUN_COUNTER = 0;
    private Integer RUNNUNG_IN_WORK_COUNTER = 0;
    private Integer RUNNUNG_IN_SHAHAR = 0;
    private Integer MASSADA_COUNTER = 0;
    private Integer NACHAL_COUNTER = 0;

    /* Arrays of participants in each event */
    private String[] GOLAN_RACE_Array = new String[2];
    private String[] NIGHT_RUN_COUNTER_Array = new String[2];
    private String[] RUNNUNG_IN_WORK_COUNTER_Array = new String[2];
    private String[] RUNNUNG_IN_SHAHAR_Array = new String[2];
    private String[] MASSADA_COUNTER_Array = new String[2];
    private String[] NACHAL_COUNTER_Array = new String[2];

    private final static  String FRIENDS_GOING = " of your friends are going";

    /* all app users hash map*/

    private HashMap<String, User> usersMap;

    /* A list of the details of the events */
    private ArrayList<String> mEventDetails = new ArrayList<>();

    /* A list of the sign uo urls of the events */
    private ArrayList<Integer> mSighUp = new ArrayList<>();

    private ArrayList<String> mFriendsGoing = new ArrayList<>();

    private ArrayList<Button> mButtons = new ArrayList<>();


    //******************  Buttons and fields ****************//

                      //  Action Bar Buttons //
    /* A button that navigated to the home page screen */
    private Button homePageButton;

    /* A button that navigated to the matched partners screen */
    private Button partnersButton;

    /* A button that navigated to the Profile screen */
    private Button profileButton;

    private Button joinButton;

    /**
     * Creates the buttons and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_acticity);

        currentUser = RunningMatchHomePage.currentUser;
        currentUserMatches =(ArrayList<String>) getIntent().getSerializableExtra("userMatches");
        usersMap = (HashMap<String, User>) getIntent().getSerializableExtra("usersMap");

        homePageButton = (Button) findViewById(R.id.action_bar_homepage_3);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();

            }

        });

        partnersButton = (Button) findViewById(R.id.action_bar_partners_3);
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

        profileButton = (Button) findViewById(R.id.action_bar_profile_3);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });

        joinButton = (Button) findViewById(R.id.join);

        Log.d(TAG, "onCreate: started.");

        setMatchesGoingToEvent();
        initImageBotmap();
    }

    /**
     * set the number of friends going to the same event
     */
    private void setMatchesGoingToEvent() {
        if (currentUserMatches != null) {
            for (String mail : currentUserMatches) {
                User cur = usersMap.get(mail);
                if (cur != null) {
                    for (String raceName: cur.getEvents()){
                            switch (raceName){
                                case "Golan Race":
                                    if (GOLAN_RACE_COUNTER < 2){
                                        GOLAN_RACE_Array[GOLAN_RACE_COUNTER] = cur.getUserName();
                                    }
                                    GOLAN_RACE_COUNTER++;
                                    break;
                                case "Night Run":
                                    if (NIGHT_RUN_COUNTER < 2) {
                                        NIGHT_RUN_COUNTER_Array[NIGHT_RUN_COUNTER] = cur.getUserName();
                                    }
                                    NIGHT_RUN_COUNTER++;
                                    break;
                                case "Running in Work":
                                    if (RUNNUNG_IN_WORK_COUNTER < 2) {
                                        RUNNUNG_IN_WORK_COUNTER_Array[RUNNUNG_IN_WORK_COUNTER] = cur.getUserName();
                                    }
                                    RUNNUNG_IN_WORK_COUNTER++;
                                    break;
                                case "Running With Shahar":
                                    if (RUNNUNG_IN_SHAHAR < 2) {
                                        RUNNUNG_IN_SHAHAR_Array[RUNNUNG_IN_SHAHAR] = cur.getUserName();
                                    }
                                    RUNNUNG_IN_SHAHAR++;
                                    break;
                                case "Half Marathon Arad Masada":
                                    if (MASSADA_COUNTER < 2) {
                                        MASSADA_COUNTER_Array[MASSADA_COUNTER] = cur.getUserName();
                                    }
                                    MASSADA_COUNTER++;
                                    break;
                                case "Nachal Race":
                                    if (NACHAL_COUNTER < 2) {
                                        NACHAL_COUNTER_Array[NACHAL_COUNTER] = cur.getUserName();
                                    }
                                    NACHAL_COUNTER++;
                                    break;
                            }
                        }
                    }

                }
            }
        }


    /**
     * Initialize the events information
     */
    private void initImageBotmap() {
        Log.d(TAG, "initImageBotmap: prapering bitmap");

        mImagesURI.add("http://pipman.co.il/wp-content/uploads/banners-Paskal-3-1200x630-300x158.jpg");
        mNames.add("Megido Race");
        mEventDetails.add("Majdal Shams, 04.09.19");
        mSighUp.add(R.string.golan_link);
        mButtons.add(joinButton);
        friendsGoingHelper(GOLAN_RACE_Array,GOLAN_RACE_COUNTER);

        mImagesURI.add("http://www.biblemarathon.co.il/webfiles/Gallery/3/815/bible2016run.JPG");
        mNames.add("Night Run");
        mEventDetails.add("Kiryat Gat, 12.09.19");
        mSighUp.add(R.string.kiryat_gat_link);
        mButtons.add(joinButton);
        friendsGoingHelper(NIGHT_RUN_COUNTER_Array,NIGHT_RUN_COUNTER);

        mImagesURI.add("https://i.ytimg.com/vi/vkP2kygkl6c/hqdefault.jpg");
        mNames.add("Running in Work");
        mEventDetails.add("Herzliya, 07.09.19");
        mSighUp.add(R.string.work_link);
        mButtons.add(joinButton);
        friendsGoingHelper(RUNNUNG_IN_WORK_COUNTER_Array,RUNNUNG_IN_WORK_COUNTER);

        mImagesURI.add("https://cdn.isnet.co.il/dyncontent/tmp/267/2018_5_14_fb9e3423-12df-4f52-af78-5250ae9c5876_510_1000_Fit_.png");
        mNames.add("Running With Shahar");
        mEventDetails.add("Alonei Habashan, 19.09.19");
        mSighUp.add(R.string.shahar_link);
        mButtons.add(joinButton);
        friendsGoingHelper(RUNNUNG_IN_SHAHAR_Array,RUNNUNG_IN_SHAHAR);

        mImagesURI.add("https://www.shvoong.co.il/wp-content/uploads/2014/10/52151-300x1992.jpg");
        mNames.add("Half Marathon");
        mEventDetails.add("Arad, 15.09.19");
        mSighUp.add(R.string.arad_link);
        mButtons.add(joinButton);
        friendsGoingHelper(MASSADA_COUNTER_Array,MASSADA_COUNTER);

        mImagesURI.add("https://www.shvoong.co.il/wp-content/uploads/2017/08/Nahal_july.png");
        mNames.add("Nachal Race");
        mEventDetails.add("Tel Aviv, 25.09.19");
        mSighUp.add(R.string.nachal_link);
        mButtons.add(joinButton);
        friendsGoingHelper(NACHAL_COUNTER_Array,NACHAL_COUNTER);

        initRecyclerView();
    }

    private void friendsGoingHelper(String[] lst, Integer counter){
        if(lst[0] == null){
            mFriendsGoing.add("Be the first of your friends to attent this event!");
        }
        else if(lst[1] == null){
            mFriendsGoing.add(lst[0] + " is going to this event, talk to him!");
        }
        else{
            if (counter == 2){
                mFriendsGoing.add(lst[0] + " and " + GOLAN_RACE_Array[1] +" are going to this event");
            }
            else if(counter > 2){
                mFriendsGoing.add(lst[0] + ", " + lst[1] + counter + FRIENDS_GOING);
            }
        }

    }

    /**
     * initialize the recycle view
     */
    private void initRecyclerView(){
        User currentUser = (User)getIntent().getSerializableExtra("user");
        Log.d(TAG, "initRecyclerView: init recyclerView");
        RecyclerView recyclerViewAdapter = findViewById(R.id.event_recycler);
        EventRecyclerAdapter adapter = new EventRecyclerAdapter(this, mNames, mImagesURI,
                mEventDetails, mSighUp, mButtons, mFriendsGoing,currentUser);
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
        partnersIntent.putExtra("user", currentUser);
        partnersIntent.putExtra("usersMap", usersMap);
        partnersIntent.putExtra("userMatches", currentUser.getMatches());
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
