package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Represents the user's Profile page
 */
public class Profile extends AppCompatActivity {

    private FirebaseFirestore fireStoreDatabase;


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

    private EditText userName;
    private EditText email;
    private EditText phoneNumber;
    private ArrayList<String> times;






    /**
     * Creates the buttons and their listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        fireStoreDatabase = FirebaseFirestore.getInstance();


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

        setNumberPickers();
        setRadioButton();
        setTexts();
        setCheckBox();

    }

    private void setTexts(){

        userName = (EditText) findViewById(R.id.profile_username);
        userName.setText(currentUser.getUserName());
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newName = userName.getText().toString();
                currentUser.setUserName(newName);
                fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("userName", newName);

            }

            @Override
            public void afterTextChanged(Editable s) { }

        });

        email = findViewById(R.id.profile_email);
        email.setText(currentUser.getEmail());
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newEmail = email.getText().toString();
                currentUser.setEmail(newEmail);
                fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("email", newEmail);

            }

            @Override
            public void afterTextChanged(Editable s) { }

        });

        phoneNumber = findViewById(R.id.profile_phone_number);
        phoneNumber.setText(currentUser.getPhoneNumber());
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newNumber = email.getText().toString();
                currentUser.setPhoneNumber(newNumber);
                fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("phoneNumber", newNumber);

            }

            @Override
            public void afterTextChanged(Editable s) { }

        });



    }


    private void setNumberPickers(){
        // creates the km spinner
        pickerKm = findViewById(R.id.profile_km);
        pickerKm.setMinValue(1);
        pickerKm.setMaxValue(50);
        pickerKm.setValue(Integer.parseInt(currentUser.getKm()));

        pickerKm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerKm.getValue();
                String km = val.toString();
                currentUser.setKm(km);
                fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("km", km);

            }
        });

        // creates the time spinner
        pickerMin = findViewById(R.id.profile_min);
        pickerMin.setMinValue(1);
        pickerMin.setMaxValue(200);
        pickerMin.setValue(Integer.parseInt(currentUser.getTime()));
        pickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerMin.getValue();
                String time = val.toString();
                currentUser.setTime(time);
                fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("time", time);


            }
        });



    }

    private void setRadioButton(){
        RadioGroup rg = (RadioGroup) findViewById(R.id.profile_gender);
        String oldGender = currentUser.getGender();
        switch(oldGender){
            case "male":
                rg.check(R.id.profile_male);
                break;
            case "female":
                rg.check(R.id.profile_female);
                break;
            case "other":
                rg.check(R.id.profile_other);
                break;
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.profile_male:
                        currentUser.setGender("male");
                        fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("gender", "male");
                        break;
                    case R.id.profile_female:
                        currentUser.setGender("female");
                        fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("gender", "female");
                        break;

                    case R.id.profile_other:
                        currentUser.setGender("other");
                        fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("gender", "other");
                        break;
                }
            }
        });

    }

    private void setCheckBox(){

        times = currentUser.getTimes();

        CheckBox morning = findViewById(R.id.profile_morning);
        CheckBox noon =  findViewById(R.id.profile_noon);
        CheckBox evening =  findViewById(R.id.profile_evening);
        CheckBox anyTime =  findViewById(R.id.profile_anytime);

        for(String time: times){
            switch (time){
                case "morning":
                    morning.setChecked(true);
                    break;
                case "noon":
                    noon.setChecked(true);
                    break;
                case "evening":
                    evening.setChecked(true);
                case "any time":
                    anyTime.setChecked(true);
            }

        }

        //get times this users likes running
        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    times.add("morning");
                }else{
                    times.remove("morning");

                }
            }
        });

        noon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    times.add("noon");
                }
                else{
                    times.remove("noon");

                }
            }
        });

        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    times.add("evening");
                } else{
                    times.remove("evening");

                }

            }
        });


        anyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((CheckBox) v).isChecked()){
                    times.remove("any time");
                }else{
                    times.remove("any time");

                }

            }
        });

        currentUser.setTimes(times);
        fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("times", times);


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
