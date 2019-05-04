package com.example.myapplication3;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> mImageNames= new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
//    private ArrayList<String> mDistances = new ArrayList<>();
    private ArrayList<String> mPace = new ArrayList<>();
    private ArrayList<String> mLocation = new ArrayList<>();
    private ArrayList<String> mInfo = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages, ArrayList<String> mPace, ArrayList<String> mLocation, ArrayList<String> mInfo) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
//        this.mDistances = mDistance;
        this.mLocation = mLocation;
        this.mPace = mPace;
        this.mInfo= mInfo;
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
//        holder.distance.setText(mDistances.get(position));
        holder.Pace.setText(mPace.get(position));
        holder.location.setText(mLocation.get(position));
        holder.info.setText(mInfo.get(position));


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ProfileGallery.class);
                intent.putExtra("image", mImages.get(position));
                intent.putExtra("profile name", mImageNames.get(position));
                intent.putExtra("location", mLocation.get(position));
                intent.putExtra("pace", mPace.get(position));
//                intent.putExtra("distances", mDistances.get(position));
                intent.putExtra("info", mInfo.get(position));



                mContext.startActivity(intent);
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
//        TextView distance;
        TextView Pace;
        TextView location;
        TextView info;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.profile_image);
            imageName=itemView.findViewById(R.id.runners_name);
//            distance =itemView.findViewById(R.id.prifile_other_distances_input);
            Pace = itemView.findViewById(R.id.profile_other_pace_input);
            location = itemView.findViewById(R.id.profile_other_location_input);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            info = itemView.findViewById(R.id.profile_other_info);

        }
    }

//    public void profileOthers() {
//         Create an Intent to start the second activity
//        Intent profileOthersIntent = new Intent(this.mContext, ProfileGallery.class);
//         Start the new activity.
//        mContext.startActivity(profileOthersIntent);
//
//    }
}