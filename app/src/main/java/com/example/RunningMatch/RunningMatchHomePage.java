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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;

public class RunningMatchHomePage extends AppCompatActivity {
    private Toolbar myToolBar;
    private Button profileButton;
    private Button homepageButton;
    private Button matchButton;
    private Button popupButton;


    // card slide suggestions
    private ViewPager viewPager;
    private SlideAdapter myadapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private ArrayList<User> usersArray= new ArrayList<User>();

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_tab);
        context = this;

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        getUsers();

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


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        popupButton = (Button)findViewById(R.id.lets_run);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RunningMatchHomePage.this, pop.class));
            }
        });

    }

    private void getUsers(){
        mDataBase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                usersArray.clear();
                while (items.hasNext()){
                    DataSnapshot item = items.next();
                    String email = item.getKey();
                    String name, km, time, phoneNumber, description,gender ;
                    name = item.child("userName").getValue().toString();
                    km = item.child("km").getValue().toString();
                    time = item.child("time").getValue().toString();
                    phoneNumber = item.child("phoneNumber").getValue().toString();
                    description = item.child("userDescription").getValue().toString();
                    gender = item.child("gender").getValue().toString();

                    User user = new User(email, phoneNumber, km, time, name, description, gender);
                    usersArray.add(user);
                }

                myadapter = new SlideAdapter(context, usersArray);
                viewPager.setAdapter(myadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void profile() {
        // Create an Intent to start the second activity
        Intent profileIntent = new Intent(this, profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }

    public void partners() {
        // Create an Intent to start the second activity
        Intent partnersIntent = new Intent(this, partners_list.class);

        // Start the new activity.
        startActivity(partnersIntent);

    }


}