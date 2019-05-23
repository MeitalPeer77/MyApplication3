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

public class Register_step_two extends AppCompatActivity {

    private DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;
    Spinner spinnerKm;
    Spinner spinnerMin;
    String password;
    String email;
    String phone;
    String name;
    String gender;
    EditText userDescription;
    String description;

    String[] kmArray = {"1", "2", "3", "4","5", "6","7", "8","9", "10", "11", "12", "13", "14", "15"};
    String [] minArray = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60"};

    private Button nextbutton;
    private StorageReference mStorage;
    private Button mSelectImage;
    private ProgressDialog progressDialog;
    private ImageView mImageView;

    private static final int GALERRY_INTENT = 2;


    Bundle extras;
    String km;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_two);

        userDescription = (EditText) findViewById(R.id.editText5);

        mStorage = FirebaseStorage.getInstance().getReference();

        mSelectImage = (Button) findViewById(R.id.uploadImage);

        mImageView = (ImageView) findViewById(R.id.imageView2);

        progressDialog = new ProgressDialog(this);

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




        nextbutton = (Button) findViewById(R.id.done_register);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
    }



    protected void registerNext() {

        mAuth = FirebaseAuth.getInstance();
//        createAccount(email, password);


//        String id = databaseUsers.push().getKey();
        description  = userDescription.getText().toString();
//        User newUser = new User(email, phone, km, time, name, description, gender, longitude, latitude);
        email = email.replace(".", "");
//        databaseUsers.child("users").child(email).setValue(newUser);

        Intent loginIntent = new Intent(this, LocationScreen.class);
        loginIntent.putExtra("email", email);
        loginIntent.putExtra("password", password);
        loginIntent.putExtra("phoneNumber", phone);
        loginIntent.putExtra("userName", name);
        loginIntent.putExtra("km", km);
        loginIntent.putExtra("description", description);
        loginIntent.putExtra("gender", gender);
        loginIntent.putExtra("time", time);

        // Start the new activity.
        startActivity(loginIntent);

    }

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

                    Glide.with(Register_step_two.this)
                            .asBitmap()
                            .load(generatedFilePath)
                            .into(mImageView);

                    //Picasso.get().load(generatedFilePath).fit().centerCrop().into(mImageView);

                    progressDialog.dismiss();

                    Toast.makeText(Register_step_two.this, "upload done", Toast.LENGTH_LONG).show();

                }
            });


        }
    }

}
