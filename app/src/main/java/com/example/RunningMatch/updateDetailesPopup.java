package com.example.RunningMatch;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class updateDetailesPopup extends AppCompatActivity {


    //******************  Buttons and fields ****************//
    /* Update details button */
    private Button updateButton;

    /* Do not update now button */
    private Button notNow;

    /* The bundle for getting the information from the previous screen */
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detailes_popup);

        extras = getIntent().getExtras();
        updateButton = (Button)findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                profile();
            }
        });

        notNow = (Button)findViewById(R.id.notNow);
        notNow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                suggestions();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels*0.8);
        int high = (int) (dm.heightPixels*0.4);
        getWindow().setLayout(width, high);
    }

    /**
     * Go back to suggestion page
     */
    public void suggestions(){
        // Create an Intent to start the  activity
        Intent suggestionsIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(suggestionsIntent);
    }

    public void profile(){
        // Create an Intent to start the  activity
        Intent suggestionsIntent = new Intent(this, Profile.class);

        // Start the new activity.
        startActivity(suggestionsIntent);

    }
}
