package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class partners_list extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mDistance = new ArrayList<>();
    private ArrayList<String> mPace = new ArrayList<>();
    private Button matchButton;
    private Button profileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_tab);
        initImageBitMap();

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
        mImageUrls.add("https://i.ibb.co/6bcnB4V/runner5.png");
        mNames.add("Runners Name");
        mDistance.add("3.1");
        mPace.add("5.5");


        mImageUrls.add("https://i.ibb.co/d5cgNH1/runner1.png");
        mNames.add("Runners Name");
        mDistance.add("1.5");
        mPace.add("5");

        mImageUrls.add("https://i.ibb.co/9HZfDHy/runner2.png");
        mNames.add("Runners Name");
        mDistance.add("0.5");
        mPace.add("4.5");

        mImageUrls.add("https://i.ibb.co/L5C4KJk/runner3.png");
        mNames.add("Runners Name");
        mDistance.add("2.0");
        mPace.add("6");

        mImageUrls.add("https://i.ibb.co/q99CG0T/runner4.png");
        mNames.add("Runners Name");
        mDistance.add("2.5");
        mPace.add("5.5");

        initRecycleView();
    }

    private void initRecycleView(){
        RecyclerView recycleView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, mDistance, mPace);
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
        Intent suggestiosIntent = new Intent(this, RunningMatch.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }
}
