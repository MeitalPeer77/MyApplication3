package com.example.RunningMatch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class pop extends Activity {
    private Button contactButton;
    private Button notNow;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);


        extras = getIntent().getExtras();
        final String phoneNumber = extras.getString("phoneNumber");

        contactButton = (Button)findViewById(R.id.contact);
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

    public void MainProfile(){
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, ProfileGallery.class);

        // Start the new activity.
        startActivity(loginIntent);

    }

    public void suggestions(){
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(loginIntent);

    }
}
