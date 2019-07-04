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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private String myLikesArray ="myLikesArray";
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

    static final User[] user= new User[1];


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
                usersMap.remove(user.getEmail());
                ArrayList<User> users = new ArrayList<User>(usersMap.values());
                myAdapter = new RunningMatchSlideAdapter(context, users);
                viewPager.setAdapter(myAdapter);
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

                WriteBatch batch = fireStoreDatabase.batch();
                batch.set(busRef_1, user);

                DocumentReference busRef_2 = fireStoreDatabase.collection("users").
                        document(currentUserEmail).collection("matches").document(user.getEmail());

                batch.set(busRef_2, user);

                String phone = user.getPhoneNumber();

                usersMap.remove(user.getEmail());
                ArrayList<User> users = new ArrayList<User>(usersMap.values());
                myAdapter = new RunningMatchSlideAdapter(context, users);
                viewPager.setAdapter(myAdapter);

                Intent popup = new Intent(RunningMatchHomePage.this, MatchingPopUP.class);
                popup.putExtra("phoneNumber", phone);
                startActivity(popup);

            }

                });

    }

    /**
     * Gets all Users except me
     */
    private void getUsers(){
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

                                User otherUser = new User(email, phoneNumber, km, time, name, description, gender, latitude, longtitude, "", "");

                                if (!email.equals(currentUserEmail)){
                                    usersMap.put(email, otherUser);
                                }
                                else{
                                    currentUser = otherUser;
                                }
                            }
                            ArrayList<User> users = new ArrayList<User>(usersMap.values());
                            myAdapter = new RunningMatchSlideAdapter(context, users);
                            viewPager.setAdapter(myAdapter);
                        }
                    }
                });
    }
//
//        mDataBase = FirebaseDatabase.getInstance().getReference();
//
//        mDataBase.child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
//                usersMap.clear();
//                while (items.hasNext()){
//                    DataSnapshot   item = items.next();
//                    String email = item.getKey();
//                    String name, km, time, phoneNumber, description,gender, latitude, longtitude ,myLikes, myMatches;
//                    name = item.child("userName").getValue().toString();
//                    km = item.child("km").getValue().toString();
//                    time = item.child("time").getValue().toString();
//                    phoneNumber = item.child("phoneNumber").getValue().toString();
//                    description = item.child("userDescription").getValue().toString();
//                    gender = item.child("gender").getValue().toString();
//                    latitude = item.child("latitude").getValue().toString();
//                    longtitude = item.child("longitude").getValue().toString();
//
//                    //todo: what is the purpose of it?
//                    if (item.child("myLikesArray").getValue() == null){
//                        myLikes = "";
//                    }
//                    else {
//                        myLikes = item.child("myLikesArray").getValue().toString();
//                    }
//
//                    if (item.child("matches").getValue() == null){
//                        myMatches = "";
//                    }
//                    else {
//                        myMatches = item.child("matches").getValue().toString();
//                    }
//
//                    //TODO: Enter tha rate calculator. not showing everybody!
//                    if(!email.equals(currentUserEmail)) {
//
//                        User user = new User(email, phoneNumber, km, time, name, description, gender, latitude, longtitude, myLikesArray, matches);
////                        double distance = CalculateRate.calculateDistance(currentUser, user);
////                        if (distance <= currentUser.getDistanceRangeFromUser()) {
////                            usersArray.add(user);
////                        }
//
//                        usersMap.put(email, user);


//                    }
//                }

//                RateComparator sorter = new RateComparator(currentUser);
//                Collections.sort(usersArray, sorter);



//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

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
        partnersIntent.putExtra("userArray", usersArray);

        // Start the new activity.
        startActivity(partnersIntent);

    }

    /**
     * Transfer to event page
     */
    public void event() {
        // Create an Intent to start the second activity
        Intent eventIntent = new Intent(this, EventActivity.class);

        // Start the new activity.
        startActivity(eventIntent);

    }



}