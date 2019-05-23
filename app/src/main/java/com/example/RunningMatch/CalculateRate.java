package com.example.RunningMatch;


import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CalculateRate  extends AppCompatActivity {

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static double calculateDistance(User mainUser, User user) {
        double myDistance = distance(Double.parseDouble(mainUser.getLatitude()), Double.parseDouble(mainUser.getLongitude()), Double.parseDouble(user.getLatitude()), Double.parseDouble(user.getLongitude()));
        return myDistance;

    }
    private static int distanceRate (User mainUser, User user){
        double myDistance = calculateDistance(mainUser, user);
        double distanceRange = mainUser.getDistanceRangeFromUser();

        if (myDistance < distanceRange/(double)3) {
            return 3;
        }
        else if (myDistance < distanceRange*((double)2/(double) 3)) {
            return 2;
        }
        else if (myDistance < distanceRange) {
            return 1;
        }
        return 0;
    }

    private static int paceRate (User mainUser, User user) {
        double myPace = Double.parseDouble(mainUser.getTime())/Double.parseDouble(mainUser.getKm());
        double userPace = Double.parseDouble(user.getTime())/Double.parseDouble(user.getKm());

        if (myPace*1.05 > userPace  && userPace > myPace*0.95) {
            return 3;
        }
        else if (myPace*1.1 > userPace  && userPace > myPace*0.9) {
            return 2;
        }
        else if (myPace*1.15 > userPace  && userPace > myPace*0.85) {
            return 1;
        }
        return 0;

    }

    private  int kmeRate (User mainUser, User user) {
        if (mainUser.getKm() == user.getKm()) {
            return 3;
        } else if (Integer.parseInt(user.getKm()) + 1 == Integer.parseInt(mainUser.getKm()) || Integer.parseInt(user.getKm()) - 1 == Integer.parseInt(mainUser.getKm())) {
            return 2;
        } else if (Integer.parseInt(user.getKm()) + 2 == Integer.parseInt(mainUser.getKm()) || Integer.parseInt(user.getKm()) - 2 == Integer.parseInt(mainUser.getKm())) {
            return 1;
        }
        return 0;
    }

    public double rateCalculator(User mainUser, User user) {

        double distRate = distanceRate(mainUser, user)*0.3;
        double paceRate = paceRate(mainUser, user)*0.4;
        double kmtRate = kmeRate(mainUser, user)*0.3;
        return distRate+paceRate+kmtRate;
    }

}
