package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerbutton;
    private Button tabButton;
    private Toolbar appBar;

    //meital's try

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });



        registerbutton = (Button) findViewById(R.id.button2);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });

        tabButton = (Button) findViewById(R.id.button5);
        tabButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tab();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    public void tab() {
        // Create an Intent to start the second activity
        Intent GroupIntent = new Intent(this, Tabs.class);

        // Start the new activity.
        startActivity(GroupIntent);

    }
    public void login() {
        // Create an Intent to start the second activity
        Intent loginIntent = new Intent(this, login.class);

        // Start the new activity.
        startActivity(loginIntent);

    }

    public void register() {
        // Create an Intent to start the second activity
        Intent RegisterIntent = new Intent(this, Register22.class);

        // Start the new activity.
        startActivity(RegisterIntent);

    }
}
