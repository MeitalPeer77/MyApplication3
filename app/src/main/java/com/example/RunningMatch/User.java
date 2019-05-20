package com.example.RunningMatch;

public class User {

    String userId;

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
}
