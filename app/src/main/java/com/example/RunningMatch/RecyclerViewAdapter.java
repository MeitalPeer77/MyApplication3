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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> mImageNames= new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mLocation = new ArrayList<>();
    private ArrayList<String> mDistances = new ArrayList<>();
    private ArrayList<String> mPace = new ArrayList<>();
    private Context mContext;

    private ArrayList<User> mMatches;

    public RecyclerViewAdapter(Context mContext,ArrayList<User> mMatches, ArrayList<String> mImageNames, ArrayList<String> mImages,ArrayList<String> mLocation, ArrayList<String> mDistance, ArrayList<String> mPace) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.mDistances = mDistance;
        this.mLocation= mLocation;
        this.mPace = mPace;

        this.mMatches = mMatches;
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
        holder.imageName.setText(mMatches.get(position).getUserName());
        //holder.Pace.setText(mPace.get(position));


        /////try location
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String email = mAuth.getCurrentUser().getEmail();
        email.replace(".", "");
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
        final double[] distance = new double[1];
        mDataBase.child("users").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double latitude = Double.parseDouble(dataSnapshot.child(email).child("latitude").getValue().toString());
                double longitude = Double.parseDouble(dataSnapshot.child(email).child("longitude").getValue().toString());
                distance[0] = CalculateRate.distance(latitude, longitude,Double.parseDouble(mMatches.get(position).getLatitude()), Double.parseDouble(mMatches.get(position).getLongitude()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

            });

            ///try location
        holder.location.setText(String.valueOf((float)distance[0]));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ProfileGallery.class);
                intent.putExtra("image", mImages.get(position));
                intent.putExtra("profile name", mMatches.get(position).getUserName());
                intent.putExtra("location", mLocation.get(position));
                intent.putExtra("pace", mMatches.get(position).getTime());
                intent.putExtra("distances", mMatches.get(position).getKm());

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
        TextView location;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.partner_list_image);
            imageName=itemView.findViewById(R.id.partner_list_name);
            location = itemView.findViewById(R.id.partner_list_location);
            parentLayout=itemView.findViewById(R.id.parent_layout);

        }
    }

}