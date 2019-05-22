package com.example.RunningMatch;

import com.example.RunningMatch.CalculateRate;
import com.example.RunningMatch.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Comparator;

public class RateComperator implements Comparator<User> {
    CalculateRate calculator = new CalculateRate();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public RateComperator(String currentUser){
        this.currentUser = currentUser;
    }

    @Override
    public int compare(User o1, User o2) {
        Double user1Rate = calculator.rateCalculator(currentUser, o1);
        Double user2Rate = calculator.rateCalculator(currentUser, o2);
        Double user1Distance = CalculateRate.calculateDistance(currentUser, o1);
        Double user2Distance = CalculateRate.calculateDistance(currentUser, o2);

        if (user2Rate.compareTo(user1Rate) == 0) {
            return user1Distance.compareTo(user2Distance);
        }
        else {return user2Rate.compareTo(user1Rate);}
    }
}
