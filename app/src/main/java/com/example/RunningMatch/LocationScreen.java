package com.example.RunningMatch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents the last step of registration activity- location screen
 */
public class LocationScreen extends AppCompatActivity {

    //******************  Buttons and fields ****************//

    /* The button of moving to the next screen */
    private Button enableLocation;

    /* Shows the location of the user*/
    private TextView locationView;

    /* Location manager of Android */
    private LocationManager locationManager;

    /* Location listener of Android */
    private LocationListener listener;

    /* The email the user entered */
    String email;

    /* The email the user entered without "." */
    String cleanEmail;

    /* The bundle of the extras from the previous screen*/
    Bundle extras;

    /* The password the user entered */
    String password;

    /* The phone the user entered */
    String phone;

    /* The mane the user entered */
    String name;

    /* Initial location longitude of the user */
    String longitude = "0";

    /* Initial location latitude of the user */
    String latitude = "0";

    /* The km specified by the user*/
    String km;

    /* The gender the user entered */
    String gender;

    /* The time specified by the user */
    private String time;

    /* The description the user entered */
    private String description;

    private ArrayList<String> goals;

//    /* The new user after getting all his information*/
//    User newUSer;

    //******************  Firebase Objects ****************//

    /* Represents the database */
    DatabaseReference databaseReference;

    /* The authentication object of the app */
    private FirebaseAuth mAuth;

    /* Represents the FireStore database */

    FirebaseFirestore fireStoreDatabase;




    /**
     * Creates the buttons and their listeners, and takes the information from the previous screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fireStoreDatabase = FirebaseFirestore.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_location);

        locationView = (TextView) findViewById(R.id.textView5);
        enableLocation = (Button) findViewById(R.id.lets_run);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        extras = getIntent().getExtras();
        password = extras.getString("password");
        email = extras.getString("email");
        phone = extras.getString("phoneNumber");
        name = extras.getString("userName");
        km = extras.getString("km");
        gender = extras.getString("gender");
        time = extras.getString("time");
        description = extras.getString("description");
        goals = extras.getStringArrayList("goals");



        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String long_1 = String.valueOf(location.getLongitude());
                final String lat_1 = String.valueOf(location.getLatitude());

                databaseReference.child("users").child(email).child("longitude").setValue(long_1);
                databaseReference.child("users").child(email).child("latitude").setValue(lat_1);

                final DocumentReference busRef = fireStoreDatabase.collection("users").document(email.replace(".", ""));
                busRef.update("longitude", long_1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        busRef.update("latitude", lat_1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }

                        });
                    }

                });

            }

            public String getLatitude(Location location){
                Double lat;
                lat = location.getLatitude();

                if (lat!= null){
                    return (String.valueOf(location.getLatitude()));
                }
                else{
                    return "0";
                }
            }
            public String getLongitude(Location location){

                Double longtitude_get;
                longtitude_get = location.getLongitude();

                if (longtitude_get!= null){
                    return (String.valueOf(location.getLongitude()));
                }
                else{
                    return "0";
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        configure_button();
    }

    /**
     * Requests a permission from the user to get it's location
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    /**
     * Takes the current location from the user
     */
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }


        // this code won'locationView execute IF permissions are not allowed, because in the line above there is return statement.
        enableLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
//                databaseReference = FirebaseDatabase.getInstance().getReference();
//                cleanEmail = email;
//                email = email.replace(".", "");
//                User newUser = new User(email, phone, km, time, name, description, gender, longitude, latitude, "", "");
//                databaseReference.child("users").child(email).setValue(newUser);
//                RunningMatchHomePage.currentUser = newUser;
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);

                cleanEmail = email;
                email = email.replace(".", "");
                User newUser = new User(email, phone, km, time, name, description, gender, longitude, latitude, "", "", goals);

                //todo: delete if using firestore
                databaseReference.child("users").child(email).setValue(newUser);
                createAccount(cleanEmail, password, newUser);

//                suggestions();
//                addUser(newUser);
            }
        });
    }


    /**
     * Creates a new user and adds it to DB
     */
    public void addUser(User user){
        final DocumentReference busRef = fireStoreDatabase.collection("users").document(user.getEmail());
        busRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                busRef.collection("myLikesArray");
                busRef.collection("matches");
            }

        });


    }
    /**
     * Go to next page- suggestions
     */
    public void suggestions() {
        // Create an Intent to start the second activity
        Intent suggestiosIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }

    /**
     * Creates an account from the user
     * @param email
     * @param password
     */
    private void createAccount(String email, String password, final User newUser) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            addUser(newUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LocationScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

}