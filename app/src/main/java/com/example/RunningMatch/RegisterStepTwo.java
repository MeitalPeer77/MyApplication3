package com.example.RunningMatch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

/**
 * Presents the register step two screen
 */
public class RegisterStepTwo extends AppCompatActivity {

    //******************  Buttons and fields ****************//

    /* The spinner for entering km */
    NumberPicker pickerKm;

    /* The spinner for entering time */
    NumberPicker pickerMin;

    /* The password the user entered */
    String password;

    /* The email the user entered */
    String email;

    /* The phone the user entered */
    String phone;

    /* The mane the user entered */
    String name;

    /* The gender the user entered */
    String gender;

    /* The times of day the user likes running */
    ArrayList<String> times = new ArrayList<>();



    /* The field of the user description */
    EditText userDescription;

    /* The description the user entered */
    String description;

    ArrayList<String> goals;

    /* The next button of the screen */
    private Button nextButton;

    /* The button for uploading an image */
    private ImageView mSelectImage;

    private ToggleButton toggleGoal1;
    private ToggleButton toggleGoal2;
    private ToggleButton toggleGoal3;
    private ToggleButton toggleGoal4;
    private ToggleButton toggleGoal5;
    private ToggleButton toggleGoal6;

    private ProgressDialog progressDialog;

    /* The inage uploaded by the user */
    private ImageView mImageView;

    /* Used for opening the gallery of the user */
    private static final int GALERRY_INTENT = 2;

    /* Used for get the user's information from the previous screen */
    Bundle extras;

    /* The km specified by the user */
    String km;

    /* The time specified by the user */
    String time;


    //******************  Firebase Objects ****************//

    /* Represents the database */
    private DatabaseReference databaseUsers;

    /* The authentication object of the app */
    private FirebaseAuth mAuth;

    /* Firebase object which stores the picture of the user*/
    private StorageReference mStorage;



    /**
     * Creates the buttons, fields and the listeners of the screen.
     * Gets the information from the previous screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_two);

        userDescription = (EditText) findViewById(R.id.editText5);
        mStorage = FirebaseStorage.getInstance().getReference();
        mImageView = (ImageView) findViewById(R.id.profile_pucture_upload);
        progressDialog = new ProgressDialog(this);

        nextButton = (Button) findViewById(R.id.done_register);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
        mSelectImage = (ImageView) findViewById(R.id.profile_pucture_upload);
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType("image/*");

                startActivityForResult(intent, GALERRY_INTENT);
            }
        });

        extras = getIntent().getExtras();
        password = extras.getString("password");
        email = extras.getString("email");
        phone = extras.getString("phoneNumber");
        name = extras.getString("userName");
        gender = extras.getString("userName");



        databaseUsers = FirebaseDatabase.getInstance().getReference();


        // creates the km spinner
        pickerKm = findViewById(R.id.spinner_km);
        pickerKm.setMinValue(1);
        pickerKm.setMaxValue(50);
        pickerKm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerKm.getValue();
                km = val.toString();
            }
        });



        // creates the time spinner
        pickerMin = findViewById(R.id.spinner_min);
        pickerMin.setMinValue(1);
        pickerMin.setMaxValue(200);
        pickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Integer val= pickerMin.getValue();
                time = val.toString();
            }
        });

        //get times this users likes running
        times.add("any time");
        CheckBox morning = (CheckBox) findViewById(R.id.morning);
        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    times.add("morning");
                }
            }
        });

        CheckBox noon = (CheckBox) findViewById(R.id.noon);
        noon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    times.add("noon");
                }
            }
        });

        CheckBox evening = (CheckBox) findViewById(R.id.evening);
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    times.add("evening");
                }
            }
        });


        CheckBox anyTime = (CheckBox) findViewById(R.id.anytime);
        anyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((CheckBox) v).isChecked()){
                    times.remove("any time");
                }
            }
        });

        goals = new ArrayList<>();

        toggleGoal1 = (ToggleButton) findViewById(R.id.run_marathon);
        toggleGoal1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    goals.add("marathon");
            }
        });
        toggleGoal2 = (ToggleButton) findViewById(R.id.stay_in_shape);
        toggleGoal2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    goals.add("stay in shape");
            }
        });
        toggleGoal3 = (ToggleButton) findViewById(R.id.run_half_marathon);
        toggleGoal3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    goals.add("run half marathon");
            }
        });

        toggleGoal4 = (ToggleButton) findViewById(R.id.run_5K);
        toggleGoal4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    goals.add("run 5K");
            }
        });
        toggleGoal5 = (ToggleButton) findViewById(R.id.run_for_fun);
        toggleGoal5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    goals.add("run for fun");
            }
        });
        toggleGoal6 = (ToggleButton) findViewById(R.id.run_10K);
        toggleGoal6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    goals.add("run 10K");
            }
        });

    }



    /**
     * Transfer the user's information to the next register screen and open it
     */
    protected void registerNext() {

        description  = userDescription.getText().toString();

        Intent registerNextIntent = new Intent(this, LocationScreen.class);
        registerNextIntent.putExtra("email", email);
        registerNextIntent.putExtra("password", password);
        registerNextIntent.putExtra("phoneNumber", phone);
        registerNextIntent.putExtra("userName", name);
        registerNextIntent.putExtra("km", km);
        registerNextIntent.putExtra("description", description);
        registerNextIntent.putExtra("gender", gender);
        registerNextIntent.putExtra("time", time);
        registerNextIntent.putStringArrayListExtra("goals", goals);
        registerNextIntent.putStringArrayListExtra("times", times);

        // Start the new activity.
        startActivity(registerNextIntent);

    }

    /**
     * Uploading a photo fron the user
     * @param requsetCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requsetCode, int resultCode, Intent data){
        super.onActivityResult(requsetCode, resultCode, data);
        if (requsetCode == GALERRY_INTENT && resultCode == RESULT_OK){

            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            Uri uri = data.getData();

            StorageReference filePath = mStorage.child("Photos").child(uri.getLastPathSegment());


            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                    Uri generatedFilePath = downloadUri.getResult();

                    Glide.with(RegisterStepTwo.this)
                            .asBitmap()
                            .load(generatedFilePath)
                            .into(mImageView);

                    //Picasso.get().load(generatedFilePath).fit().centerCrop().into(mImageView);

                    progressDialog.dismiss();

                    Toast.makeText(RegisterStepTwo.this, "upload done", Toast.LENGTH_LONG).show();

                }
            });


        }
    }

}
