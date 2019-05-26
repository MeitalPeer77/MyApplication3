package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class partners_list extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mDistance = new ArrayList<>();
    private ArrayList<String> mPace = new ArrayList<>();
    private ArrayList<String> mLocations = new ArrayList<>();
    private DatabaseReference mDataBase;
    public ArrayList<User> matchesArray;
    private FirebaseAuth mAuth;


    private Button matchButton;
    private Button profileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_tab);
        getMatches();
        initImageBitMap();
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();
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


    private void initImageBitMap(){
        //mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/runningmatch-f9e90.appspot.com/o/Photos%2F6433?alt=media&token=2deb2ceb-13bc-4d0b-8969-5640ea8530af");
        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/10570528_663942363682968_3472551542158599282_n.jpg?_nc_cat=108&_nc_ht=scontent.ftlv6-1.fna&oh=4b5e98824fb8295aad9b55de97be83be&oe=5D6910B9");
        mNames.add("Nathaniel Tavisal");
        mDistance.add("5");
        mPace.add("35");
  //      mInfo.add("info 1");
        mLocations.add("0.7");


        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/314570_2286081785515_743884461_n.jpg?_nc_cat=108&_nc_ht=scontent.ftlv6-1.fna&oh=72538202dcbfa0c5da9f3f8aeb3b8435&oe=5D75FC76");
        mNames.add("Meital Peer");
        mDistance.add("5");
        mPace.add("30");
//        mInfo.add("info 2");
        mLocations.add("1.3");

        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t31.0-8/21122579_1833916359957237_6719538012298659073_o.jpg?_nc_cat=109&_nc_ht=scontent.ftlv6-1.fna&oh=b0fd68b0be099e148062a31a3d20a348&oe=5D2AC9E7");
        mNames.add("Shira Weitman");
        mDistance.add("6");
        mPace.add("35");
        mLocations.add("1.8");


        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/559079_10151809848077388_1166275349_n.jpg?_nc_cat=102&_nc_ht=scontent.ftlv6-1.fna&oh=7aade7d7463f3d5ed16c32c2f30562ef&oe=5D326041");
        mNames.add("Michal Gordon");
        mDistance.add("4");
        mPace.add("25");
        mLocations.add("1.7");


        mImageUrls.add("https://scontent.ftlv6-1.fna.fbcdn.net/v/t1.0-9/10898096_10152956054335912_5153974417356391101_n.jpg?_nc_cat=109&_nc_ht=scontent.ftlv6-1.fna&oh=f131a44166cc0a4d57b4737366da0a16&oe=5D6512E9");
        mNames.add("Liav Alter");
        mDistance.add("4");
        mPace.add("25");
        mLocations.add("1.9");


        initRecycleView();
    }

    private void initRecycleView(){
        RecyclerView recycleView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, mLocations, mDistance, mPace);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void profile() {
        // Create an Intent to start the second activity
        Intent profileIntent = new Intent(this, profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }

    public void suggestions() {
        // Create an Intent to start the second activity
        Intent suggestiosIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }

    private void getMatches(){
        mDataBase = FirebaseDatabase.getInstance().getReference();
        String email = mAuth.getCurrentUser().getEmail();

        mDataBase.child("users").child(email).child("matches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
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
                    myLikes = item.child("myLikesArray").getValue().toString();
                    myMatches = item.child("matches").getValue().toString();


                        User user = new User(email, phoneNumber, km, time, name, description, gender, latitude, longtitude, myLikes, myMatches);
                        matchesArray.add(user);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
