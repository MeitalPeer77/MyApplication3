package com.example.RunningMatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Presents the first regoster screen of the app- Create an account
 */
public class RegisterStepOne extends AppCompatActivity {

    //******************  Buttons and fields ****************//
    /* The next button of the screen */
    private Button nextbutton;

    /* The field object of entering email */
    private EditText mEmail;

    /* The field object of entering password */
    private EditText mPassword;

    /* The field object of entering username */
    private EditText mUserName;

    /* The field object of entering phone number */
    private EditText mPhoneNumber;

    /* The gender the user entered */
    String gender;


    //******************  Firebase Objects ****************//
    /* Represents the database */
    DatabaseReference databaseUsers;

    /**
     * Creates the button and the fields and set a listener fo "next" button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_step_one);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        mEmail = (EditText) findViewById(R.id.inputemail);
        mPassword = (EditText) findViewById(R.id.password);
        mUserName = (EditText) findViewById(R.id.username);
        mPhoneNumber = (EditText) findViewById(R.id.phonenumber);
        mUserName = (EditText) findViewById(R.id.username);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton:
                        gender = "male";
                        // do operations specific to this selection
                        break;
                    case R.id.radioButton2:
                        gender = "female";
                        // do operations specific to this selection
                        break;

                    case R.id.radioButton3:
                        gender = "other";
                        // do operations specific to this selection
                        break;
                }
            }
        });


        nextbutton = (Button) findViewById(R.id.next_register);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
    }

    /**
     * Transfer the user with his information to register step two screen
     */
    public void registerNext() {
        // Create an Intent to start the activity of the next register screen
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String userName = mUserName.getText().toString();

        Intent registerNextIntent = new Intent(this, RegisterStepTwo.class);
        registerNextIntent.putExtra("email", email);
        registerNextIntent.putExtra("password", password);
        registerNextIntent.putExtra("phoneNumber", phoneNumber);
        registerNextIntent.putExtra("userName", userName);
        registerNextIntent.putExtra("gender", gender);

        // Start the new activity.
        startActivity(registerNextIntent);

    }

    /**
     * Used for authentication
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    };
}
