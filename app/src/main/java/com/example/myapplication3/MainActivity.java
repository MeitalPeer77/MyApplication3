package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView SignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RuningMatch();
            }
        });

        SignInButton = (TextView) findViewById(R.id.sign_up);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });


    }

    public void RuningMatch(){
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, RunningMatch.class);

        // Start the new activity.
        startActivity(loginIntent);

    }

    public void register() {
        // Create an Intent to start the second activity
        Intent RegisterIntent = new Intent(this, Register_step_one.class);

        // Start the new activity.
        startActivity(RegisterIntent);

    }

}
