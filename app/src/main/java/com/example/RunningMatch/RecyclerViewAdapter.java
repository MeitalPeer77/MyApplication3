package com.example.RunningMatch;


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
    private ArrayList<String> mLocation = new ArrayList<>();
    private ArrayList<String> mDistances = new ArrayList<>();
    private ArrayList<String> mPace = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages,ArrayList<String> mLocation, ArrayList<String> mDistance, ArrayList<String> mPace) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.mDistances = mDistance;
        this.mLocation= mLocation;
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

        //layout_list_item holders
        holder.imageName.setText(mImageNames.get(position));
        holder.location.setText(mLocation.get(position));
//        holder.Pace.setText(mPace.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ProfileGallery.class);
                intent.putExtra("image", mImages.get(position));
                intent.putExtra("profile name", mImageNames.get(position));
                intent.putExtra("location", mLocation.get(position));
                intent.putExtra("pace", mPace.get(position));
                intent.putExtra("distances", mDistances.get(position));

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
        TextView Pace;
        TextView location;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.partner_list_image);
            imageName=itemView.findViewById(R.id.partner_list_name);
//            Pace = itemView.findViewById(R.id.partner_list_pace);
            location = itemView.findViewById(R.id.partner_list_location);
            parentLayout=itemView.findViewById(R.id.parent_layout);

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