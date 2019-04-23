package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.support.v7.app.ActionBar;


import java.util.ArrayList;

public class RunningMatch extends AppCompatActivity {
    private Toolbar myToolBar;
    private Button profileButton;


    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mInfo = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);
        initImageBitMap();

        profileButton = (Button) findViewById(R.id.action_bar_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                profile();
            }
        });
        android.widget.TabHost host = (android.widget.TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        android.widget.TabHost.TabSpec spec = host.newTabSpec("suggestions");
        spec.setContent(R.id.Suggestions);
        spec.setIndicator("suggestions");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("groups");
        spec.setContent(R.id.Groups);
        spec.setIndicator("groups");
        host.addTab(spec);

    }

private void initImageBitMap(){
        mImageUrls.add("data:image/jpeg;base64,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");
        mNames.add("Meital Peer");
        mInfo.add("looking for a partner to go on morning runs with on sundays");

        mImageUrls.add("https://media.istockphoto.com/photos/man-running-outdoors-picture-id636342222?k=6&m=636342222&s=612x612&w=0&h=TfwQOj70Fed1dnSS8rKIHImFSHKVD0ODSdXwY8VljHc=");
        mNames.add("Shira Weitman");
    mInfo.add("want to run a marathon, looking for a partner to help me reach this goal");


    initRecycleView();
}

private void initRecycleView(){
    RecyclerView recycleView = findViewById(R.id.recycler_view);
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, mInfo);
    recycleView.setAdapter(adapter);
    recycleView.setLayoutManager(new LinearLayoutManager(this));
}

public void profile() {
        // Create an Intent to start the second activity
        Intent profileIntent = new Intent(this, profile.class);

        // Start the new activity.
        startActivity(profileIntent);

    }
}