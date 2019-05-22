package com.example.RunningMatch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
//    private LoginButton loginButton;
    private Button tryButton; //just for test location
    private TextView SignInButton;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;
    private Button loginButton;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseUsers = FirebaseDatabase.getInstance().getReference();

        mEmailField = (EditText) findViewById(R.id.email);
        mPasswordField = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    DatabaseReference ref =  databaseUsers.getDatabase().getReference("users");

                    String email =  firebaseAuth.getCurrentUser().getEmail();
                    email = email.replace(".", "");
                    String km = ref.child(email).child("km").toString();
                    String name = this.getClass().getSimpleName();
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
                    if (name.equals("MainActivity")){
                        Register2();
                    }



                    else {
                        RunningMatch();
                    }
                }
            }
        };


        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startSignIn();

            }
        });

        SignInButton = (TextView) findViewById(R.id.sign_up_main_activity);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });

        tryButton = (Button) findViewById(R.id.tryButton);
        tryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                location();
            }
        });

    }

    public void RunningMatch(){
        // Create an Intent to start the second activity
        Intent homeIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(homeIntent);

    }

    public void Register2(){
        // Create an Intent to start the second activity
        Intent register2Intent = new Intent(this, Register_step_two.class);

        // Start the new activity.
        startActivity(register2Intent);

    }

    public void register() {
        // Create an Intent to start the second activity
        Intent RegisterIntent = new Intent(this, Register_step_one.class);
        // Start the new activity.
        startActivity(RegisterIntent);

    }

    public void location() {
        // Create an Intent to start the second activity
        Intent locationIntent = new Intent(this, LocationScreen.class);
        // Start the new activity.
        startActivity(locationIntent);

    }

    public void startSignIn(){
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}