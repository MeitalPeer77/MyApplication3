package com.example.RunningMatch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RunningMatchHomePage extends AppCompatActivity {
    private Toolbar myToolBar;
    private Button profileButton;
    private Button homepageButton;
    private Button matchButton;
    private Button popupButton;

    //event
    private Button eventButton;

    public static User currentUser;
    public String currentUserEmail;
    private Button not4meButton;


    // card slide suggestions
    private ViewPager viewPager;
    private SlideAdapter myadapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private ArrayList<User> usersArray= new ArrayList<User>();
    private HashMap<String, User> usersMap = new HashMap<String, User>();
    private String myLikesArray ="myLikesArray";
    private String matches="matches";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_tab);
        context = this;
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mDataBase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentUserEmail = mAuth.getCurrentUser().getEmail();
                currentUserEmail = currentUserEmail.replace(".", "");
                String km = dataSnapshot.child(currentUserEmail).child("km").getValue().toString();
                String time = dataSnapshot.child(currentUserEmail).child("time").getValue().toString();
                String description = dataSnapshot.child(currentUserEmail).child("userDescription").getValue().toString();
                String gender = dataSnapshot.child(currentUserEmail).child("gender").getValue().toString();
                String user_name = dataSnapshot.child(currentUserEmail).child("userName").getValue().toString();
                String distanceRange = dataSnapshot.child(currentUserEmail).child("distanceRangeFromUser").getValue().toString();
                String phoneNumber = dataSnapshot.child(currentUserEmail).child("phoneNumber").getValue().toString();
                String longi = dataSnapshot.child(currentUserEmail).child("longitude").getValue().toString();
                String lati = dataSnapshot.child(currentUserEmail).child("latitude").getValue().toString();


                currentUser = new User(currentUserEmail,phoneNumber, km, time, user_name, description, gender, lati, longi, myLikesArray, matches);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profileButton = (Button) findViewById(R.id.action_bar_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });
//;
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


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        getUsers();

        not4meButton = (Button) findViewById(R.id.reject);
        not4meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                final User user = myadapter.users.get(a);
                usersMap.remove(user.getEmail());
                ArrayList<User> users = new ArrayList<User>(usersMap.values());
                myadapter = new SlideAdapter(context, users);
                viewPager.setAdapter(myadapter);
            }

        });


        popupButton = (Button)findViewById(R.id.lets_run);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                final User user = myadapter.users.get(a);
                mDataBase.child("users").child(currentUserEmail).child("myLikesArray").child(user.getEmail()).setValue("1");

                mDataBase.child("users").child(user.getEmail()).child("myLikesArray").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()) {

                            DataSnapshot item = items.next();
                            String emailKey = item.getKey();

                            if (emailKey.equals(currentUserEmail)) {

                                mDataBase.child("users").child(currentUserEmail).child("matches").child(user.getEmail()).setValue(user);

                                String phone =(String) item.child(user.getPhoneNumber()).getValue();

                                Intent popup = new Intent(RunningMatchHomePage.this,  pop.class);
                                popup.putExtra("phoneNumber", phone);
                                startActivity(popup);

                            }
                            usersMap.remove(user.getEmail());
                            ArrayList<User> users = new ArrayList<User>(usersMap.values());
                            myadapter = new SlideAdapter(context, users);
                            viewPager.setAdapter(myadapter);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

        });
    }

    public void profile() {
        // Create an Intent to start the second activity
        Intent profileIntent = new Intent(this, profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }

    private void getUsers(){
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mDataBase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                usersMap.clear();
//                usersArray.clear();
                while (items.hasNext()){
                    DataSnapshot   item = items.next();
                    String email = item.getKey();
                    String name, km, time, phoneNumber, description,gender, latitude, longtitude ,myLikes, myMatches;
                    name = item.child("userName").getValue().toString();
                    km = item.child("km").getValue().toString();
                    time = item.child("time").getValue().toString();
                    phoneNumber = item.child("phoneNumber").getValue().toString();
                    description = item.child("userDescription").getValue().toString();
                    gender = item.child("gender").getValue().toString();
                    latitude = item.child("latitude").getValue().toString();
                    longtitude = item.child("longitude").getValue().toString();
                    if (item.child("myLikesArray").getValue() == null){
                        myLikes = "";
                    }
                    else {
                        myLikes = item.child("myLikesArray").getValue().toString();
                    }

                    if (item.child("matches").getValue() == null){
                        myMatches = "";
                    }
                    else {
                        myMatches = item.child("matches").getValue().toString();
                    }


                    if(!email.equals(currentUserEmail)) {

                        User user = new User(email, phoneNumber, km, time, name, description, gender, latitude, longtitude, myLikesArray, matches);
                        double distance = CalculateRate.calculateDistance(currentUser, user);
//                        if (distance <= currentUser.getDistanceRangeFromUser()) {
//                            usersArray.add(user);
//                        }

                        usersMap.put(email, user);


                    }
                }

//                RateComperator sorter = new RateComperator(currentUser);
//                Collections.sort(usersArray, sorter);

                ArrayList<User> users = new ArrayList<User>(usersMap.values());
                myadapter = new SlideAdapter(context, users);
                viewPager.setAdapter(myadapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void partners() {
        // Create an Intent to start the second activity
        Intent partnersIntent = new Intent(this, partners_list.class);

        // Start the new activity.
        startActivity(partnersIntent);

    }


    public void event() {
        // Create an Intent to start the second activity
        Intent eventIntent = new Intent(this, Event_acticity.class);

        // Start the new activity.
        startActivity(eventIntent);

    }



}