package com.example.RunningMatch;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Calculates the rate for each potential running partner
 */
public class CalculateRate  extends AppCompatActivity {

    /**
     * the helper method which calculates the distance from each potential running partner
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
    }

    /**
     * Converts degrees to radius
     * @param deg
     * @return
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Converts radius to degrees
     * @param rad
     * @return
     */
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * Calculates the distance between 2 users
     * @param mainUser
     * @param user
     * @return
     */
    public static double calculateDistance(User mainUser, User user) {
        double myDistance = distance(Double.parseDouble(mainUser.getLatitude()), Double.parseDouble(mainUser.getLongitude()), Double.parseDouble(user.getLatitude()), Double.parseDouble(user.getLongitude()));
        return myDistance;

    }

    /**
     * Rates the distance between 2 users
     * @param mainUser
     * @param user
     * @return
     */
    private static int distanceRate (User mainUser, User user){
        double myDistance = calculateDistance(mainUser, user);
        double distanceRange = Integer.parseInt(mainUser.getDistanceRangeFromUser());

        if (myDistance < distanceRange/(double)3) {
            return 25;
        }
        else if (myDistance < distanceRange*((double)2/(double) 3)) {
            return 17;
        }
        else if (myDistance < distanceRange) {
            return 10;
        }
        return 0;
    }

    /**
     * Rates the pace of two users
     * @param mainUser
     * @param user
     * @return
     */
    private static int paceRate (User mainUser, User user) {
        double myPace = Double.parseDouble(mainUser.getTime())/Double.parseDouble(mainUser.getKm());
        double userPace = Double.parseDouble(user.getTime())/Double.parseDouble(user.getKm());

        if (myPace*1.05 > userPace  && userPace > myPace*0.95) {
            return 30;
        }
        else if (myPace*1.1 > userPace  && userPace > myPace*0.9) {
            return 20;
        }
        else if (myPace*1.15 > userPace  && userPace > myPace*0.85) {
            return 10;
        }
        return 0;

    }

    /**
     * Rates the km of 2 users
     * @param mainUser
     * @param user
     * @return
     */
    private  int kmRate(User mainUser, User user) {
        if (mainUser.getKm() == user.getKm()) {
            return 25;
        } else if (Integer.parseInt(user.getKm()) + 1 == Integer.parseInt(mainUser.getKm()) || Integer.parseInt(user.getKm()) - 1 == Integer.parseInt(mainUser.getKm())) {
            return 17;
        } else if (Integer.parseInt(user.getKm()) + 2 == Integer.parseInt(mainUser.getKm()) || Integer.parseInt(user.getKm()) - 2 == Integer.parseInt(mainUser.getKm())) {
            return 10;
        }
        return 0;
    }

    private int goalsRate(User mainUser, User user){
        ArrayList<String> userGoals = user.getGoals();
        for (String goal : mainUser.getGoals()){
            if(userGoals.contains(goal)){
                return 10;
            }
        }
        return 0;
    }

    private int timesRate(User mainUser, User user) {
        ArrayList<String> userTimes = user.getTimes();
        for (String time : mainUser.getTimes()) {
            if (userTimes.contains(time)) {
                return 10;
            }
        }
        return 0;
    }

    private int likeRate(User mainUser, User user){
        ArrayList<String> likesArray = (ArrayList<String>) user.getMyLikesArray();
        String myName = mainUser.getEmail().replace(".", "");
        for (String runner : likesArray){
            if(runner.equals(myName)){
                return 100;
            }
        }
        return 0;
    }



    /**
     * Returns a final matching rate for 2 users
     * @param mainUser
     * @param user
     * @return
     */
    public int rateCalculator(User mainUser, User user) {

        int distRate = distanceRate(mainUser, user);
        int paceRate = paceRate(mainUser, user);
        int kmtRate = kmRate(mainUser, user);
        int goalsRate = goalsRate(mainUser, user);
        int timeRate = timesRate(mainUser, user);
        int likeRate = likeRate(mainUser, user);
        return distRate+paceRate+kmtRate+goalsRate+timeRate+likeRate;
    }

}
