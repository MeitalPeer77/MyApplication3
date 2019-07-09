package com.example.RunningMatch;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * The slide adapter of Running match home page
 */
public class RunningMatchSlideAdapter extends PagerAdapter {

    /* The context of the activity */
    Context context;

    /* The inflater which enters the information to the slides*/
    LayoutInflater inflater;

    /* Potential running partners array*/
    ArrayList<User> users;

    User currentUser;

    /* Calculate the rate of a potential partner */
    CalculateRate calculator = new CalculateRate();


    /* The images of the potential running partners */
    int[] lstImages = {
            R.drawable.netta,
            R.drawable.maayanm,
            R.drawable.yehonatans,
            R.drawable.noam,
            R.drawable.liors,
            R.drawable.toms,
            R.drawable.almoga, R.drawable.almoga, R.drawable.almoga

    };

    /* The names of the potential running partners */
    public String[] lstNames = {
            "Netta Zohar",
            "Maayan Yossef Magenheim",
            "Jehonathan Spigelman",
            "Noa Menasheof",
            "Lior Saadon",
            "Tom Saltsberg",
            "Almog Argaman"

    }   ;

    /* The descriptions of the potential running partners */
    public String[] lst_description = {
            "Looking for a running partner who will keep me motivated!",
            "I just want to stay in shape with short runs once a week.",
            "Running the marathon next year and need a partner to train with.",
            "Looking for a running partner. Twice a week. Early mornings.",
            "I like running at night. 10pm at the earliest.",
            "I hate running but want to stay in shape. Help me do this!",
            "I'm a serious sprinter. Catch me if you can!",

    };

    /**
     * Creates a Slide Adapter object
     * @param context
     * @param users
     */
    public RunningMatchSlideAdapter(Context context, ArrayList<User> users, User currentUser) {
        this.context = context;
        this.users = users;
        this.currentUser = currentUser;
    }

    /**
     * Gets the number of potential users
     * @return
     */
    @Override
    public int getCount() {
        return users.size();
    }

    /**
     * Checks if the view is an object?
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    /**
     * Enters the information of the potential partners to the slides
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card,container,false);

        ImageView imgslide = (ImageView)  view.findViewById(R.id.card_image);
        TextView txttitle= (TextView) view.findViewById(R.id.txttitle);
        TextView time = (TextView) view.findViewById(R.id.time_input);
        TextView km = (TextView) view.findViewById(R.id.km_input);
        TextView distance = (TextView) view.findViewById(R.id.distance_input);
        TextView teezer = (TextView) view.findViewById(R.id.teezer);

        RecyclerView goalsAdapter = view.findViewById(R.id.goals_adapter);
        GoalsAdapter adapter = new GoalsAdapter(context, users.get(position));
        goalsAdapter.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        goalsAdapter.setLayoutManager(layoutManager);


        String userName = users.get(position).getUserName();
//        String des = users.get(position).getUserDescription();
        String timeInput = users.get(position).getTime();
        String kmInput = users.get(position).getKm();

        double currentLat = Double.parseDouble(RunningMatchHomePage.currentUser.getLatitude());
        double currentLong = Double.parseDouble(RunningMatchHomePage.currentUser.getLongitude());
        double usersLat = Double.parseDouble(users.get(position).getLatitude());
        double usersLong = Double.parseDouble(users.get(position).getLongitude());
        double doubleDistance = calculator.distance(currentLat, currentLong, usersLat, usersLong);
        float distanceInput = (float) doubleDistance;



        imgslide.setImageResource(R.mipmap.ic_launcher_round);
        txttitle.setText(userName);
        teezer.setText(getTeezer(position, distanceInput));
        container.addView(view);
        time.setText(timeInput);
        km.setText(kmInput);
        DecimalFormat df = new DecimalFormat("#.#");
        distance.setText(df.format(distanceInput));

        return view;
    }

    /**
     * Removes item from the slides
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    // get a relevent teezer for a specific user
    private String getTeezer(int position, float distance){
        String eventTeezer = "omg! you are both going to the ";
        String distanceTeezer = "wow, you two are in  spitting distance from each other!";
        String goalTeezer = " this is so great! you have a common goal. together you will definitely ";
        String kmTeezer = "you two run the same disnances! a partner from heaven...";

        ArrayList<String> possibleTeezers = new ArrayList<>();

        User otherUser = users.get(position);

        //km teezer
        if(otherUser.getKm().equals(currentUser.getKm()))
            possibleTeezers.add(kmTeezer);

        if(distance < 0.5){
            possibleTeezers.add(distanceTeezer);
        }

        // goal teezer
        ArrayList<String > othersGoals = otherUser.getGoals();
        for (String goal: currentUser.getGoals()) {
            if (othersGoals.contains(goal)) {
                possibleTeezers.add(goalTeezer+goal);
                break;
            }
        }

        //event teezer
        ArrayList<String > othersEvents = otherUser.getEvents();
        for (String event: currentUser.getEvents()){
            if(othersEvents.contains(event)){
                possibleTeezers.add(eventTeezer+event);
                break;
            }
        }
        if(possibleTeezers.isEmpty()){
            return "I have a good feeling about this one!";
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(possibleTeezers.size());
        return possibleTeezers.get(randIndex);

    }


}