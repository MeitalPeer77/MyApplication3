

package com.example.RunningMatch;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventRecyclerAdapet extends RecyclerView.Adapter<EventRecyclerAdapet.ViewHolder>{

    private ArrayList<String> mImageName = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mDetails = new ArrayList<>();
    private ArrayList<Integer> mSignUp = new ArrayList<>();
    private Context mContext;


    public EventRecyclerAdapet(Context mContext, ArrayList<String> mImageName,
                               ArrayList<String> mImages, ArrayList<String> mEventDetails,
                               ArrayList<Integer> mRegister) {
        this.mImageName = mImageName;
        this.mImages = mImages;
        this.mDetails = mEventDetails;
        this.mSignUp = mRegister;
        this.mContext = mContext;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageName.get(position));
        holder.eventDetails.setText(mDetails.get(position));
        holder.sighUpText.setText(mSignUp.get(position));

    }


    @Override
    public int getItemCount() {
        return mImageName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        TextView eventDetails;
        RelativeLayout parentLayout;
        TextView sighUpText;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            image = itemView.findViewById(R.id.event_image);
            imageName = itemView.findViewById(R.id.event_name);
            eventDetails = itemView.findViewById(R.id.event_details);
            parentLayout = itemView.findViewById(R.id.event_parent_layout);

            sighUpText = itemView.findViewById(R.id.sign_up_event);
            sighUpText.setMovementMethod(LinkMovementMethod.getInstance());


        }
    }


}