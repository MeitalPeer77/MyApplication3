package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

                    // Screen Buttons //
    /* Button to personal details page */
    private Button personalDetailsBtn;

    /* Sign out button */
    private Button signOutBtn;


    /**
     * Creates the buttons and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);



        signOutBtn = (Button) findViewById(R.id.sign_out);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }

        });
        homePageButton = (Button) findViewById(R.id.action_bar_homepage);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();

            }

        });
        partnersButton = (Button) findViewById(R.id.action_bar_matches);
        if (RunningMatchHomePage.showPartnerNotofication){
            partnersButton.setBackgroundResource(R.drawable.group2);
        }
        else{partnersButton.setBackgroundResource(R.drawable.ic_launcher);}
        partnersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partners();

            }

        });
        personalDetailsBtn = (Button) findViewById(R.id.edit_button);
        personalDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetails();

            }

        });

        eventButton = (Button)findViewById(R.id.action_bar_event);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event();
            }
        });

    }

    /**
     * Transfer to settings page
     * @param view
     */
    public void btnSetting_onClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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
