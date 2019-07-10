package com.example.RunningMatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class represents the user in running match-app
 */
public class User implements Serializable {
    /* The id of the user */
    private String userId;

    /* The name of the user*/
    private String userName;

    /* The gender of the user*/
    private String gender;

    /* The km the user running */
    private String km;

    /* The time the user running */
    private String time;

    /* The description of the user */
    private String userDescription;

    /* The maximum possible from a running partner */
    private String DistanceRangeFromUser = "20";

    /* The phone number of the user */
    private String phoneNumber;

    /* The location latitude of the user */
    private String latitude = "0";

    /* The location longitude of the user*/
    private String longitude= "0";

    /* The email of the user */
    private String email;

    /* List of the goals the user want to achieve */
    private ArrayList<String> goals;

    /* Events the user is going to*/
    private ArrayList<String> events;

    /* Times in the day that the user want to run*/
    private ArrayList<String> times;

    /* Runner the user does't want to run with */
    private ArrayList<String> not4me;

    private Date signInTime;


    //TODO: check how it is on firebase
    private ArrayList<String> myLikesArray;
    private ArrayList<String> matches;

    /**
     * Creates a User object
     * @param email
     * @param phoneNumber
     * @param km
     * @param time
     * @param userName
     * @param userDescription
     * @param gender
     * @param latitude
     * @param longitude
     * @param myLikesArray
     * @param matches
     *
     */
    public User(String email,  String phoneNumber, String km, String time, String userName,
                String userDescription, String gender, String latitude, String longitude ,
                ArrayList<String> myLikesArray, ArrayList<String> matches, ArrayList<String> not4me,
                ArrayList<String> goals, ArrayList<String> times, ArrayList<String> events){
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.km = km;
        this.time = time;
        this.userName = userName;
        this.userDescription = userDescription;
        this.gender = gender;
        this.longitude = longitude;
        this.latitude = latitude;
        this.myLikesArray = myLikesArray;
        this.matches = matches;
        this.goals = goals;
        this.times = times;
        this.not4me = not4me;
        this.events = events;
        this.signInTime = new Date();

    }

    public User(){

    }
    /**
     * Get the user's ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the User's ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's description
     */
    public String getUserDescription() {
        return userDescription;
    }

    /**
     * Sets the user's description
     */
    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    /**
     * Gets the user's email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Gets the user's km
     */
    public String getKm() {
        return this.km;
    }

    /**
     * Sets the user's description
     */
    public void setKm(String km) {
        this.km = km;
    }

    /**
     * Gets the user's running time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the user's running time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets the user's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the user's username
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Sets the user's username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's maximum distance from a running partner
     */
    public String getDistanceRangeFromUser() {
        return DistanceRangeFromUser;
    }

    /**
     * Sets the user's maximum distance from a running partner
     */
    public void setDistanceRangeFromUser(String distance){
        DistanceRangeFromUser = distance;
    }

    /**
     * Gets the user's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user's location latitude
     */
    public String  getLatitude() {
        return latitude;
    }

    /**
     * Sets the user's location latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the user's location longitude
     */
    public String  getLongitude() {
        return longitude;
    }

    /**
     * Sets the user's location longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getMyLikesArray() {
        return myLikesArray;
    }

    public void setMyLikesArray(ArrayList<String> lst) {
        this.myLikesArray= lst;
    }

    public ArrayList<String> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<String> matches) {
        this.matches = matches;
    }

    public ArrayList<String> getGoals(){
        return this.goals;
    }

    public void setGoals(ArrayList<String> goals){
        this.goals = goals;
    }

    public ArrayList<String> getTimes(){
        return this.times;
    }

    public void setTimes(ArrayList<String> times){
        this.times = times;
    }

    public ArrayList<String> getEvents(){
        return this.events;
    }

    public void setevents(ArrayList<String> events){
        this.events = events;
    }


    public ArrayList<String> setSingleEvent(String event){
        this.events.add(event);
        return this.events;
    }

    public ArrayList<String> removeSigleEvent(String event){
        this.events.remove(event);
        return this.events;
    }

    public boolean isInRange(User otherUser){
        double distance = CalculateRate.calculateDistance(this, otherUser);
        return (distance <= Double.parseDouble(this.getDistanceRangeFromUser()));
    }

    public ArrayList<String> getNot4me() {
        return not4me;
    }

    public void setNot4me(ArrayList<String> not4me) {
        this.not4me = not4me;
    }

    public Date getSignInTime(){return signInTime;}

    public void setSignInTime(Date newDate) {this.signInTime = newDate;}

}
