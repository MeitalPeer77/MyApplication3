package com.example.RunningMatch;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.Equalizer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationScreen extends AppCompatActivity {

    DatabaseReference databaseReference;
    //private FirebaseAnalytics mFirebaseAnalytics;


    private Button b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;
    private FirebaseAuth mAuth;
    String email;
    Bundle extras;
    String password;
    String phone;
    String name;
    String longitude = "0";
    String latitude = "0";
    String km;
    String gender;
    private String time;
    private String description;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_location);

        t = (TextView) findViewById(R.id.textView5);
        b = (Button) findViewById(R.id.lets_run);

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


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                t.append("\n " + location.getLongitude() + " " + location.getLatitude());


                databaseReference.child("users").child(email).child("longitude").setValue(location.getLongitude());
                databaseReference.child("users").child(email).child("latitude").setValue(location.getLatitude());
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

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }


        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                createAccount(email, password);
//                String key_email = email;
                email = email.replace(".", "");
                User newUser = new User(email, phone, km, time, name, description, gender, longitude, latitude);
                databaseReference.child("users").child(email).setValue(newUser);
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);


                suggestions();
            }
        });
    }


    public void suggestions() {
//        createAccount(email, password);
//        User newUser = new User(email, phone, km, time, name, description, gender, longitude, latitude);
//        databaseReference.child("users").child(email).setValue(newUser);

        // Create an Intent to start the second activity
        Intent suggestiosIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(suggestiosIntent);

    }
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
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