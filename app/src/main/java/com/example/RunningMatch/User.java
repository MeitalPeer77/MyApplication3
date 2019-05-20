package com.example.RunningMatch;

public class User {
    private String userName;
    private String gender;
    private String km;
    private String time;
    private String userDescription;
    private int DistanceRangeFromUser = 20;
    private String phoneNumber;
    private String email;


    String location;

    public User(String email,  String phoneNumber, String km, String time, String userName, String userDescription, String gender){
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.km = km;
        this.time = time;
        this.userName = userName;
        this.userDescription = userDescription;
        this.gender = gender;
    }

    private String getLocation() {
        return location;
    }


    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDistanceRangeFromUser() {
        return DistanceRangeFromUser;
    }

    public void setDistanceRangeFromUser(int distance){
        DistanceRangeFromUser = distance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
