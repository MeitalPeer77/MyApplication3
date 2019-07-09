package com.example.RunningMatch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Represents the homepage screen
 */
public class RunningMatchHomePage extends AppCompatActivity {

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
    private Button popupButton;

    /* Not for me button for the current card of a potential running partner*/
    private Button not4meButton;

    // card slide suggestions
    /* The view pager of the cards */
    private ViewPager viewPager;

    /* The slide adaoter of the cards */
    private RunningMatchSlideAdapter myAdapter;

    /* User's data*/
    private ArrayList<User> usersArray= new ArrayList<User>();

    /* All the suggestions for the current user*/
    private HashMap<String, User> usersMap = new HashMap<String, User>();

    private String matches="matches";
    private Context context;

    /* The email of the current user */
    public String currentUserEmail;

    static public User currentUser;

    //******************  Firebase Objects ****************//

    /* The authentication object of the app */
    private FirebaseAuth mAuth;

    /* Represents the database */
    private DatabaseReference mDataBase;

    /* Represents the FireStore database */
    FirebaseFirestore fireStoreDatabase;

    Server server = new Server();

    static final User[] user = new User[1];


    /**
     * Get the current user's details, creates the buttons of the screen and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_tab);
//        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();
        fireStoreDatabase = FirebaseFirestore.getInstance();

        currentUserEmail = mAuth.getCurrentUser().getEmail();
        currentUserEmail = currentUserEmail.replace(".", "");

        server.getUser(currentUserEmail, user);
        context = this;

        getUsers();


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        profileButton = (Button) findViewById(R.id.action_bar_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });

        matchButton = (Button) findViewById(R.id.action_bar_matches);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();
            }
        });

        eventButton = (Button)findViewById(R.id.action_bar_event);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event();
            }
        });

        not4meButton = (Button) findViewById(R.id.reject);
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


        popupButton = (Button)findViewById(R.id.lets_run);
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


                    for (String usr : otherUserLikesArray) {
                        if (usr.equals(currentUserEmail)) {
                            String phone = user.getPhoneNumber();

                            // update other user match Array
                            ArrayList<String> otherUserMatches = user.getMatches();
                            otherUserMatches.add(currentUserEmail);
                            fireStoreDatabase.collection("users").document(user.getEmail()).update("matches", otherUserMatches);
                            user.setMatches(otherUserMatches);

                            // update current user match Array
                            ArrayList<String> myMatches = currentUser.getMatches();
                            myMatches.add(user.getEmail());
                            fireStoreDatabase.collection("users").document(currentUserEmail).update("matches", myMatches);
                            currentUser.setMatches(myMatches);
                            //pop up
                            Intent popup = new Intent(RunningMatchHomePage.this, MatchingPopUP.class);
                            popup.putExtra("phoneNumber", phone);
                            startActivity(popup);

                        }
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

                                String name, km, time, phoneNumber, description,gender, latitude, longtitude ,myLikes, myMatches;
                                name = userMap.get("userName").toString();
                                km = userMap.get("km").toString();
                                time = userMap.get("time").toString();
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

                                User otherUser = new User(email, phoneNumber, km, time, name, description, gender, latitude, longtitude, myLikesArray, matches, not4me,goals,times, events );


                                if (!email.equals(currentUserEmail)){
                                        usersMap.put(email, otherUser);

                                }else{
                                    currentUser = otherUser;
                                }
                            }

                            ArrayList<String> myLikes = currentUser.getMyLikesArray();
                            ArrayList<String> not4me = currentUser.getNot4me();

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

                            //checkLastSignIn();
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
        partnersIntent.putExtra("userArray", currentUser.getMatches());

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
        // Start the new activity.
        startActivity(eventIntent);

    }

    private boolean checkLastSignIn(){
        TimeUnit timeUnit = TimeUnit.SECONDS;
        Date date = new Date();
        currentUser.getEmail();
/*        long signInTime = currentUser.getSignInTime().getTime();
        long timeDifference = date.getTime() - signInTime;
        long timeInSeconds = timeUnit.convert(timeDifference,TimeUnit.SECONDS);
        // TODO return timeInSeconds < 30
        System.out.println(timeInSeconds < 30);
        */
        return false;
    }



}