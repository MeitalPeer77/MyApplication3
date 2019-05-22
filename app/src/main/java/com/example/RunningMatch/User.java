package com.example.RunningMatch;

public class User {
    private String userName;
    private String gender;
    private String km;
    private String time;
    private String userDescription;
    private String DistanceRangeFromUser = "20";
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private String email;


    String location;

    public User(String email,  String phoneNumber, String km, String time, String userName, String userDescription, String gender, String longitude, String latitude){
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.km = km;
        this.time = time;
        this.userName = userName;
        this.userDescription = userDescription;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public int getKm() {
        return Integer.parseInt(this.km);
    }

    public void setKm(String km) {
        this.km = km;
    }

    public int getTime() {
        return Integer.parseInt(this.time);
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
        return Integer.parseInt(DistanceRangeFromUser);
    }

    public void setDistanceRangeFromUser(String distance){
        DistanceRangeFromUser = distance;
    }

    public int getPhoneNumber() {
        return Integer.parseInt(phoneNumber);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setLatitude (String  latitude) {
        this.latitude = latitude;
    }

    public void setLongitude (String  longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return Integer.parseInt(latitude);
    }

    public double getlongitude() {
        return Integer.parseInt(this.longitude);
    }

}
