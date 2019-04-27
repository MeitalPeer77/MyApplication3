package com.example.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> mImageNames= new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mDistance = new ArrayList<>();
    private ArrayList<String> mPace = new ArrayList<>();


    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages, ArrayList<String> mDistance, ArrayList<String> mPace) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.mDistance = mDistance;
        this.mPace = mPace;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));
        holder.distance.setText(mDistance.get(position));
        holder.Pace.setText(mPace.get(position));


        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                profileOthers();
            }


        });

    }

    @Override
    public int getItemCount() {

        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        TextView distance;
        TextView Pace;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.profile_image);
            imageName=itemView.findViewById(R.id.runners_name);
            distance =itemView.findViewById(R.id.distance);
            Pace = itemView.findViewById(R.id.running_pace);
            parentLayout=itemView.findViewById(R.id.parent_layout);


        }
    }

    public void profileOthers() {
        // Create an Intent to start the second activity
        Intent profileOthersIntent = new Intent(this.mContext, profile_others.class);
        // Start the new activity.
        mContext.startActivity(profileOthersIntent);

    }
}