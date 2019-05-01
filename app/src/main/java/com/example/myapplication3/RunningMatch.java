package com.example.myapplication3;

import android.content.Intent;
import android.support.v4.view.ViewPager;
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
    private Button homepageButton;
    private Button matchButton;
    private Button popupButton;



    // card slide suggestions
    private ViewPager viewPager;
    private SlideAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_tab);
       
//         popupButton = (Button) findViewById(R.id.button);
//         popupButton.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 startActivity(new Intent(RunningMatch.this, pop.class));
//             }
//         });

//        profileButton = (Button) findViewById(R.id.action_bar_profile)
//;
        matchButton = (Button) findViewById(R.id.action_bar_matches);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();

            }

        });

//        homepageButton = (Button) findViewById(R.id.action_bar_homepage);
//        profileButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                profile();
//            }
//        });
      viewPager = (ViewPager) findViewById(R.id.viewpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);




     
//         //Tab 1
//         android.widget.TabHost.TabSpec spec = host.newTabSpec("suggestions");
//         spec.setContent(R.id.Suggestions);
//         spec.setIndicator("suggestions");
//         host.addTab(spec);

//         //Tab 2
//         spec = host.newTabSpec("groups");
//         spec.setContent(R.id.Groups);
//         spec.setIndicator("groups");
//         host.addTab(spec);

    }

//    public void profile() {
//        // Create an Intent to start the second activity
//        Intent profileIntent = new Intent(this, profile.class);
//
//        // Start the new activity.
//        startActivity(profileIntent);
//
//    }
        public void partners() {
        // Create an Intent to start the second activity
        Intent partnersIntent = new Intent(this, partners_list.class);

        // Start the new activity.
        startActivity(partnersIntent);

    }

}