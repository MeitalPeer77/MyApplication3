package com.example.RunningMatch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Represents the MatchingPopUP up screen after matching
 */
public class MatchNotInAppPopUp extends Activity {

    //******************  Buttons and fields ****************//
    /* Contact with the matched partnet button */
    private Button contactButton;

    /* Do not contact now button */
    private Button notNow;

    /* The bundle for getting the information from the previous screen */
    Bundle extras;

    /**
     * Creates the buttons and their actions
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        extras = getIntent().getExtras();
        final String phoneNumber = extras.getString("phoneNumber");
        contactButton = (Button)findViewById(R.id.update);
        contactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
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
}
