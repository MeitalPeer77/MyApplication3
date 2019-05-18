package com.example.RunningMatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private Button tryButton; //just for test location
    private TextView SignInButton;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);


//        loginButton = (Button) findViewById(R.id.login_button);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                RunningMatch();
//            }
//        });

        SignInButton = (TextView) findViewById(R.id.sign_up_main_activity);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });

        tryButton = (Button) findViewById(R.id.tryButton);
        tryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                location();
            }
        });
//
//        callbackManager = CallbackManager.Factory.create();


//        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
//        loginButton.setReadPermissions(Arrays.asList(EMAIL));
//        // If you are using in a fragment, call loginButton.setFragment(this);
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });

    }

    public void RunningMatch(){
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(loginIntent);

    }

    public void register() {
        // Create an Intent to start the second activity
        Intent RegisterIntent = new Intent(this, Register_step_one.class);
        // Start the new activity.
        startActivity(RegisterIntent);

    }

    public void location() {
        // Create an Intent to start the second activity
        Intent locationIntent = new Intent(this, Location.class);
        // Start the new activity.
        startActivity(locationIntent);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
