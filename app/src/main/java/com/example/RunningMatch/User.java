package com.example.RunningMatch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId = mAuth.getUid();
    String userName;



    String location;

    public User(String userId, String location){
        this.location = location;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getLocation() {
        return location;
    }

    public String getUserName() {
        return userName;
    }
}
