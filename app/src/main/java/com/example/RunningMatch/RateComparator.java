package com.example.RunningMatch;

import android.content.Intent;

import java.util.Comparator;

/**
 * The rate comparator for each potential running partner
 */
public class RateComparator implements Comparator<User> {

    /* The current user */
    private User currentUser;

    /* Calculate the rate for each potential user */
    private CalculateRate calculator = new CalculateRate();

    /**
     * Creates a Rate Comparator object
     * @param currentUser
     */
    public RateComparator(User currentUser){
        this.currentUser = currentUser;
    }

    /**
     * the compare method
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(User o1, User o2) {
        Integer user1Rate =(Integer) calculator.rateCalculator(currentUser, o1);
        Integer user2Rate = (Integer) calculator.rateCalculator(currentUser, o2);
        Double user1Distance = CalculateRate.calculateDistance(currentUser, o1);
        Double user2Distance = CalculateRate.calculateDistance(currentUser, o2);

        if (user2Rate.compareTo(user1Rate) == 0) {
            return user1Distance.compareTo(user2Distance);
        }
        else {return user2Rate.compareTo(user1Rate);}
    }
}
