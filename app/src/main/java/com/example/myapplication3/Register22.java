package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Register22 extends AppCompatActivity {

    private Button naxtbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register22);

        naxtbutton = (Button) findViewById(R.id.button4);
        naxtbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
    }

    public void registerNext() {
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, RegisterActivity.class);

        // Start the new activity.
        startActivity(loginIntent);

    }


}
