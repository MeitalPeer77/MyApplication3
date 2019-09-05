package com.example.RunningMatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    /*Array list of the user goals*/
    private ArrayList<String> goals = new ArrayList<>();
    /* curretn contar*/
    private Context mContext;


    /**
     ** Represents the adapter of Goals
     */
    GoalsAdapter(Context context, ArrayList<String> goals) {
        this.goals = goals;
        this.mContext = context;
    }


    private ArrayList<Integer> GoalsList(){
        ArrayList<Integer> drawables = new ArrayList<>();
        for (String goal : goals){
            switch (goal){
                case "run 10K":
                    drawables.add(R.drawable.icon_10k);
                    break;
                case "stay in shape":
                    drawables.add(R.drawable.cardiogram);
                    break;
                case "run 5K":
                    drawables.add(R.drawable.icon_5k);
                    break;
                case "marathon":
                    drawables.add(R.drawable.marathon);
                    break;
                case "run half marathon":
                    drawables.add(R.drawable.hmarathon);
                    break;
                case "run for fun":
                    drawables.add(R.drawable.run_for_fun);
                    break;
            }
        }
        return drawables;
    }


    @NonNull
    @Override
    public GoalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goals,viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull GoalsAdapter.ViewHolder viewHolder, int i) {
        ArrayList<Integer> mImages = GoalsList();
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(i))
                .into(viewHolder.image);
    }


    @Override
    public int getItemCount() {
        return goals.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.goalimage);
        }
    }


}
