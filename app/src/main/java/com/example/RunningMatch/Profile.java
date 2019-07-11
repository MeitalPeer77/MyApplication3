package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Represents the user's Profile page
 */
public class Profile extends AppCompatActivity {

    //******************  Buttons and fields ****************//

                      //  Action Bar Buttons //
    /* A button that navigated to the home page screen */
    private Button homePageButton;

    /* A button that navigated to the matched partners screen */
    private Button partnersButton;

    /* A button that navigated to the events screen */
    private Button eventButton;

    private User currentUser = RunningMatchHomePage.currentUser;

                    // Screen Buttons //
    /* Button to personal details page */
    private Button personalDetailsBtn;

    /* Sign out button */
    private TextView signOutBtn;


    /* The spinner for entering km */
    NumberPicker pickerKm;

    /* The spinner for entering time */
    NumberPicker pickerMin;

    /* The km specified by the user */
    String km;

    /* The time specified by the user */
    String time;



    /**
     * Creates the buttons and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);


        signOutBtn = (TextView) findViewById(R.id.sign_out);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }

        });

        //toolbar buttons
        homePageButton = (Button) findViewById(R.id.action_bar_homepage);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();

            }

        });
        partnersButton = (Button) findViewById(R.id.action_bar_matches);
        partnersButton.setOnClickListener(new View.OnClickListener() {
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


        // creates the km spinner
        pickerKm = findViewById(R.id.profile_km);
        pickerKm.setMinValue(1);
        pickerKm.setMaxValue(50);
        pickerKm.setValue(Integer.parseInt(currentUser.getKm()));
        pickerKm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerKm.getValue();
                km = val.toString();
            }
        });

        // creates the time spinner
        pickerMin = findViewById(R.id.profile_min);
        pickerMin.setMinValue(1);
        pickerMin.setMaxValue(200);
        pickerKm.setValue(Integer.parseInt(currentUser.getTime()));
        pickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerMin.getValue();
                time = val.toString();

            }
        });



    }


    /**
     * Sign out from the app
     */
    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent logOut = new Intent(this, MainActivity.class);
        startActivity(logOut);
    }


    /**
     * Transfer to personal details page
     */
    public void personalDetails() {
        // Create an Intent to start the activity
        Intent personalDetailsIntent = new Intent(this, PersonalDetails.class);

        // Start the new activity.
        startActivity(personalDetailsIntent);

    }

    /**
     * Transfer to home page
     */
    public void suggestions() {
        // Create an Intent to start the activity
        Intent suggestiosIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }

    /**
     * Transfer to the matches partners page
     */
    public void partners() {
        // Create an Intent to start the activity
        Intent partnersIntent = new Intent(this, PartnersList.class);

        // Start the new activity.
        startActivity(partnersIntent);

    }

    /**
     * Transfer to events page
     */
    public void event() {
        // Create an Intent to start the activity
        Intent eventIntent = new Intent(this, EventActivity.class);

        // Start the new activity.
        startActivity(eventIntent);

    }

}
