package com.example.RunningMatch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register_step_one extends AppCompatActivity {
    DatabaseReference databaseUsers;

    private Button nextbutton;
    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mUserName;
    private EditText mPhoneNumber;

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

        nextbutton = (Button) findViewById(R.id.next_register);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });
    }

    public void registerNext() {
        // Create an Intent to start the second activity
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String userName = mUserName.getText().toString();
//        mAuth = FirebaseAuth.getInstance();
//        createAccount(email, password);
//
//
////        String id = databaseUsers.push().getKey();
//
//        User newUser = new User(email, phoneNumber);
//        email = email.replace(".", "");
//        databaseUsers.child(email).setValue(newUser);
        Intent loginIntent = new Intent(this, Register_step_two.class);
        loginIntent.putExtra("email", email);
        loginIntent.putExtra("password", password);
        loginIntent.putExtra("phoneNumber", phoneNumber);
        loginIntent.putExtra("userName", userName);
        // Start the new activity.
        startActivity(loginIntent);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    };

    public void Register2(){
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, Register_step_two.class);

        // Start the new activity.
        startActivity(loginIntent);

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
                            Toast.makeText(Register_step_one.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }











}
