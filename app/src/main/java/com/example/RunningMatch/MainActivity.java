package com.example.RunningMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Presents the main Screen of the app
 */
public class MainActivity extends AppCompatActivity {

    //******************  Buttons and fields ****************//
    /* The sign in button of the screen */
    private TextView SignInButton;

    /* The log in button of the screen */
    private Button loginButton;

    /* The field object of entering email */
    private EditText mEmailField;

    /* The field object of entering password */
    private EditText mPasswordField;

    //******************  Firebase Objects ****************//
    /* The authentication object of the app */
    private FirebaseAuth mAuth;

    /* Listener object which waits for authentication orocess from the user*/
    private FirebaseAuth.AuthStateListener mAuthListener;

    /* Represents the database */
    private DatabaseReference databaseUsers;

    /* Represents the FireStore database */
    FirebaseFirestore fireStoreDatabase;

    Server server;


    /**
     * Creates the listeners for the buttons and the authentication process
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        server = new Server();

        mEmailField = (EditText) findViewById(R.id.email);
        mPasswordField = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        //create the listener in case of signing in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    RunningMatch();
                    }
                }
        };

        onStart();

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();
                startSignIn(email, password);

            }
        });

        SignInButton = (TextView) findViewById(R.id.sign_up_main_activity);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });
    }


    /**
     * Transfer to the home page of the app
     */
    public void RunningMatch() {
        // Create an Intent to start the home page activity
        Intent homeIntent = new Intent(this, RunningMatchHomePage.class);

        // Start the new activity.
        startActivity(homeIntent);

    }

    /**
     * Checks the govem email and password for signing in and takes care of it's result
     */
    public void startSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RunningMatch();
                }

            }
        });
    }
    /**
     * Transfer to register step two screen
     */
    public void Register2() {
        // Create an Intent to start regster step two screen
        Intent register2Intent = new Intent(this, RegisterStepTwo.class);

        // Start the new activity.
        startActivity(register2Intent);

    }

    /**
     * Transfer to register step one screen
     */
    public void register() {
        // Create an Intent to start the second activity
        Intent RegisterIntent = new Intent(this, RegisterStepOne.class);
        // Start the new activity.
        startActivity(RegisterIntent);

    }


    /**
     * Defines the authentication listener
     */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}