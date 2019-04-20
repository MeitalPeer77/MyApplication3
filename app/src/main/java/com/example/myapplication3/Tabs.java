package com.example.myapplication3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

public class Tabs extends AppCompatActivity {
    private Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);

        myToolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolBar);

        android.widget.TabHost host = (android.widget.TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        android.widget.TabHost.TabSpec spec = host.newTabSpec("Groups");
        spec.setContent(R.id.group_tab);
        spec.setIndicator("Groups");
        host.addTab(spec);


        //Tab 2
        spec = host.newTabSpec("Suggestions");
        spec.setContent(R.id.Suggestions);
        spec.setIndicator("suggestions");
        host.addTab(spec);
    }




}