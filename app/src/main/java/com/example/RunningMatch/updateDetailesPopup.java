package com.example.RunningMatch;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class updateDetailesPopup extends AppCompatActivity {


    //******************  Buttons and fields ****************//

    /* The spinner for entering km */
    NumberPicker pickerKm;

    /* The spinner for entering time */
    NumberPicker pickerMin;

    /* The bundle for getting the information from the previous screen */
    Bundle extras;

    /* Done button */
    private Button doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detailes_popup);
        final FirebaseFirestore fireStoreDatabase = FirebaseFirestore.getInstance();

        final User currentUser = (User)getIntent().getSerializableExtra("currentUser");


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels*0.8);
        int high = (int) (dm.heightPixels*0.4);
        getWindow().setLayout(width, high);


        // creates the km spinner
        pickerKm = findViewById(R.id.update_spinner_km);
        String current_km = currentUser.getKm();
        pickerKm.setMinValue(1);
        pickerKm.setMaxValue(50);
        pickerKm.setValue(Integer.parseInt(current_km));
        pickerKm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerKm.getValue();
                String new_km = val.toString();
                fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("km", new_km);

            }
        });

        // creates the time spinner
        pickerMin = findViewById(R.id.update_spinner_min);
        String current_min = currentUser.getTime();
        pickerMin.setMinValue(1);
        pickerMin.setMaxValue(200);
        pickerMin.setValue(Integer.parseInt(current_min));
        pickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerMin.getValue();
                String new_time = val.toString();
                fireStoreDatabase.collection("users").document(currentUser.getEmail()).update("time", new_time);
            }
        });


        doneButton = (Button)findViewById(R.id.update_btn);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestions();
            }
        });
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
