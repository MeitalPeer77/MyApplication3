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


public class Register_step_one extends AppCompatActivity {

    private Button nextbutton;
    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_one);

        nextbutton = (Button) findViewById(R.id.next_register);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerNext();
            }
        });

        mEmail = (EditText) findViewById(R.id.inputemail);
        mPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();




    }

    public void registerNext() {
        // Create an Intent to start the second activity
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        createAccount(email, password);

        Intent loginIntent = new Intent(this, Register_step_two.class);

        // Start the new activity.
        startActivity(loginIntent);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    };



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
