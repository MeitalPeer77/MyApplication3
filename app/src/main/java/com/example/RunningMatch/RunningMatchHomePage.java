package com.example.RunningMatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Represents the homepage screen
 */
public class RunningMatchHomePage extends AppCompatActivity implements Serializable {

    //******************  Buttons and fields ****************//

                  //  Action Bar Buttons //
    /* A button that navigates to the Profile screen of the user */
    private Button profileButton;

    /* A button that navigated to the matched partners screen */
    private Button matchButton;

    /* A button that navigated to the events screen */
    private Button eventButton;

                      // Cards Buttons //
    /* Match button for the current card of a potential running partner*/
    private ImageButton popupButton;

    /* Not for me button for the current card of a potential running partner*/
    private ImageButton not4meButton;

    // card slide suggestions
    /* The view pager of the cards */
    private ViewPager viewPager;

    /* The slide adaoter of the cards */
    private RunningMatchSlideAdapter myAdapter;

    /* User's data*/
    private ArrayList<User> usersArray= new ArrayList<User>();

    /* All the suggestions for the current user*/
    static HashMap<String, User> usersMap = new HashMap<String, User>();

    private String matches="matches";
    private Context context;

    /* The email of the current user */
    public String currentUserEmail;

    static public User currentUser;

    static public boolean showPartnerNotofication = false;

    //******************  Firebase Objects ****************//

    /* The authentication object of the app */
    private FirebaseAuth mAuth;

    /* Represents the database */
    private DatabaseReference mDataBase;

    /* Represents the FireStore database */
    FirebaseFirestore fireStoreDatabase;

    static final User[] user = new User[1];


    /**
     * Get the current user's details, creates the buttons of the screen and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_tab);

        mAuth = FirebaseAuth.getInstance();
        fireStoreDatabase = FirebaseFirestore.getInstance();

        currentUserEmail = mAuth.getCurrentUser().getEmail();
        currentUserEmail = currentUserEmail.replace(".", "");

        context = this;

        getUsers();

        viewPager = (ViewPager) findViewById(R.id.viewpager);


        profileButton = (Button) findViewById(R.id.action_bar_profile_1);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });

        matchButton = (Button) findViewById(R.id.action_bar_partners_1);
        if (RunningMatchHomePage.showPartnerNotofication){
            matchButton.setBackgroundResource(R.drawable.partner_notofication_shadow);
        }
        else{matchButton.setBackgroundResource(R.drawable.partner_icon_color);}
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();
            }
        });

        eventButton = (Button)findViewById(R.id.action_bar_events_1);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event();
            }
        });

        not4meButton = (ImageButton) findViewById(R.id.reject);
        not4meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                final User user = myAdapter.users.get(a);
                ArrayList<String> not4meArray = currentUser.getNot4me();
                not4meArray.add(user.getEmail());
                currentUser.setNot4me(not4meArray);
                fireStoreDatabase.collection("users").document(currentUserEmail).update("not4me", not4meArray);
                getUsers();
            }
        });


        popupButton = (ImageButton) findViewById(R.id.lets_run);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                final User user = myAdapter.users.get(a);

                // update fire store
                DocumentReference busRef_1 = fireStoreDatabase.collection("users").
                        document(currentUserEmail).collection("myLikesArray").document(user.getEmail());
                ArrayList<String> myLikes = currentUser.getMyLikesArray();
                myLikes.add(user.getEmail());
                currentUser.setMyLikesArray(myLikes);
                fireStoreDatabase.collection("users").document(currentUserEmail).update("myLikesArray", myLikes);

                ArrayList<String> otherUserLikesArray = user.getMyLikesArray();
                    if (otherUserLikesArray != null) {


                    if (otherUserLikesArray.contains(currentUser.getEmail())) {
                        user.setDoIHaveNewMatch(true);
                        String phone = user.getPhoneNumber();
                        fireStoreDatabase.collection("users").document(user.getEmail()).update("doIHaveNewMatch", true);
                        showPartnerNotofication = true;

                        // update other user match Array
                        ArrayList<String> otherUserMatches = user.getMatches();
                        otherUserMatches.add(currentUser.getEmail());
                        fireStoreDatabase.collection("users").document(user.getEmail()).update("matches", otherUserMatches);
                        user.setMatches(otherUserMatches);

                        // update current user match Array
                        ArrayList<String> myMatches = currentUser.getMatches();
                        myMatches.add(user.getEmail());
                        fireStoreDatabase.collection("users").document(currentUserEmail).update("matches", myMatches);
                        currentUser.setMatches(myMatches);

                        matchButton.setBackgroundResource(R.drawable.partner_notofication_shadow);

                        //pop up
                        Intent popup = new Intent(RunningMatchHomePage.this, MatchingPopUP.class);
                        popup.putExtra("phoneNumber", phone);
                        startActivity(popup);



                    }
                }

                String phone = user.getPhoneNumber();
                getUsers();
            }
                });
    }

    /**
     * Gets all Users except me
     */
    private void getUsers(){
        usersArray.clear();
        usersMap.clear();
        final ArrayList<User> users_arr = new ArrayList<User>();
        fireStoreDatabase.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> userMap= document.getData();

                                String email = userMap.get("email").toString();

                                String name, km, time, phoneNumber, description,gender, latitude, longtitude ,myLikes, myMatches, picUrl;
                                name = userMap.get("userName").toString();
                                km = userMap.get("km").toString();
                                time = userMap.get("time").toString();
                                picUrl = userMap.get("profilePic").toString();
                                phoneNumber = userMap.get("phoneNumber").toString();
                                description = userMap.get("userDescription").toString();
                                gender = userMap.get("gender").toString();
                                latitude = userMap.get("latitude").toString();
                                longtitude = userMap.get("longitude").toString();
                                ArrayList<String> myLikesArray = (ArrayList<String>) userMap.get("myLikesArray");
                                ArrayList<String> matches = (ArrayList<String>) userMap.get("matches");
                                ArrayList<String> not4me = (ArrayList<String>) userMap.get("not4me");
                                //TODO MAKE SURE TO ADD GOALS AND EVENTS
                                ArrayList<String> goals = (ArrayList<String>) userMap.get("goals");
                                ArrayList<String> times = (ArrayList<String>) userMap.get("times");
                                ArrayList<String> events = (ArrayList<String>) userMap.get("events");
                                boolean doIHaveNewMatch = (boolean) userMap.get(("doIHaveNewMatch")) ;

                                User otherUser = new User(email, phoneNumber, km, time, name, description, gender, latitude, longtitude, myLikesArray, matches, not4me,goals,times, events, doIHaveNewMatch, picUrl);


                                if (!email.equals(currentUserEmail)){
                                        usersMap.put(email, otherUser);

                                }else{
                                    currentUser = otherUser;
                                }
                            }

                            ArrayList<String> myLikes = currentUser.getMyLikesArray();
                            ArrayList<String> not4me = currentUser.getNot4me();
                            ArrayList<HashMap> trythis = new ArrayList<>();
                            //get only relevant users, aka are in distance range
                            for (User user: usersMap.values()) {
                                if(currentUser.isInRange(user) && !(myLikes.contains(user.getEmail())) && !(not4me.contains(user.getEmail()))){
                                    usersArray.add(user);
                                }
                            }

                            RateComparator sorter = new RateComparator(currentUser);
                            Collections.sort(usersArray, sorter);
                            myAdapter = new RunningMatchSlideAdapter(context, usersArray, currentUser);
                            myAdapter.notifyDataSetChanged();
                            viewPager.setAdapter(myAdapter);
                            //Pop- UP If i have a new match and i wans not in the app
                            if (currentUser.getDoIHaveNewMatch()){
                                showPartnerNotofication = true;
                                matchButton.setBackgroundResource(R.drawable.group2);
                                currentUser.setDoIHaveNewMatch(false);
                                fireStoreDatabase.collection("users").document(currentUserEmail).update("doIHaveNewMatch", false);
                                Intent popup = new Intent(RunningMatchHomePage.this, MatchNotInAppPopUp.class);
                                popup.putExtra("phoneNumber", "0543455456");
                                startActivity(popup);
                            }

                            Bundle extras = getIntent().getExtras();


                            checkLastSignIn();

                        }
                    }
                });

    }

    /**
     * Transfer to Profile page
     */
    public void profile() {
        // Create an Intent to start the activity
        Intent profileIntent = new Intent(this, Profile.class);

        // Start the new activity.
        startActivity(profileIntent);
    }

    /**
     * Transfer to matched partners page
     */
    public void partners() {
        // Create an Intent to start the  activity
        Intent partnersIntent = new Intent(this, PartnersList.class);
        partnersIntent.putExtra("user", currentUser);
        partnersIntent.putExtra("usersMap", usersMap);
        partnersIntent.putExtra("userMatches", currentUser.getMatches());

        // Start the new activity.
        startActivity(partnersIntent);

    }

    /**
     * Transfer to event page
     */
    public void event() {

        // Create an Intent to start the second activity
        Intent eventIntent = new Intent(this, EventActivity.class);
        eventIntent.putExtra("user", currentUser);
        eventIntent.putExtra("userMatches", currentUser.getMatches());
        eventIntent.putExtra("usersMap", usersMap);
        // Start the new activity.
        startActivity(eventIntent);
    }

    /* Checks when was the last time the user sugned in */
    private void checkLastSignIn(){
        TimeUnit timeUnit = TimeUnit.SECONDS;
        Date date = new Date();

        long signInTime = currentUser.getSignInTime().getTime();
        long timeDifference = date.getTime() - signInTime;
        long timeInSeconds = timeUnit.convert(timeDifference,TimeUnit.SECONDS);

        currentUser.setSignInTime(date);

        if(currentUserEmail.equals("liav@gmailcom")) {
            // number of seconds in two weeks
            Intent popup = new Intent(RunningMatchHomePage.this, updateDetailesPopup.class);
            popup.putExtra("currentUser", currentUser);
            startActivity(popup);
        }
    }



}