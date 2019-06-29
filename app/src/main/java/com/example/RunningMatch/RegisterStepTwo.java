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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Presents the register step two screen
 */
public class RegisterStepTwo extends AppCompatActivity {

    //******************  Buttons and fields ****************//

    /* The spinner for entering km */
    Spinner spinnerKm;

    /* The spinner for entering time */
    Spinner spinnerMin;

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

    /* The field of the user description */
    EditText userDescription;

    /* The description the user entered */
    String description;

    /* The possibilities given in the km spinner*/
    String[] kmArray = {"1", "2", "3", "4","5", "6","7", "8","9", "10", "11", "12", "13", "14", "15"};

    /* The possibilities given in the time spinner*/
    String [] minArray = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60"};

    /* The next button of the screen */
    private Button nextButton;

    /* The button for uploading an image */
    private Button mSelectImage;

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
        mImageView = (ImageView) findViewById(R.id.imageView2);
        progressDialog = new ProgressDialog(this);

        nextButton = (Button) findViewById(R.id.done_register);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
        mSelectImage = (Button) findViewById(R.id.uploadImage);
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


        databaseUsers = FirebaseDatabase.getInstance().getReference();

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

                }
            }
        });

        // creates the km spinner
        ArrayAdapter<String> arrayaddapterKm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kmArray);
        arrayaddapterKm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKm = (Spinner)findViewById(R.id.spinner_km);
        spinnerKm.setAdapter(arrayaddapterKm);
        spinnerKm.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                km = spinnerKm.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        // creates the time spinner
        ArrayAdapter<String> arrayaddapterMin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, minArray);
        arrayaddapterMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMin = (Spinner)findViewById(R.id.spinner_min);
        spinnerMin.setAdapter(arrayaddapterMin);
        spinnerMin.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                time = spinnerMin.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

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