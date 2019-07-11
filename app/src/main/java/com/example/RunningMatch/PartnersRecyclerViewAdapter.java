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

/**
 * Represents the recycler vie adapter of partners list screen
 */
public class PartnersRecyclerViewAdapter extends RecyclerView.Adapter<PartnersRecyclerViewAdapter.ViewHolder>{

    /* The images names of the partners */
    private ArrayList<String> mImageNames= new ArrayList<>();

    /* The images of the partners */
    private ArrayList<String> mImages = new ArrayList<>();

    /* The locations of the partners */
    private ArrayList<String> mLocation = new ArrayList<>();

    /* The distances from the partners */
    private ArrayList<String> mDistances = new ArrayList<>();

    private ArrayList<String> mInfo = new ArrayList<>();

    /* The paces of the partners */
    private ArrayList<String> mPace = new ArrayList<>();

    /* The context of the current activity */
    private Context mContext;

    /* The goals of the partners */
    private ArrayList<ArrayList<String>> mGoals;

    /* The goals of the partners */
    private ArrayList<ArrayList<String>> mEvents;




    /**
     * Creates a Recycle view adapter object
     * @param mContext
     * @param mImageNames
     * @param mImages
     * @param mLocation
     * @param mDistance
     * @param mPace
     */
    public PartnersRecyclerViewAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages, ArrayList<String> mLocation, ArrayList<String> mInfo,ArrayList<String> mDistance, ArrayList<String> mPace, ArrayList<ArrayList<String>> mGoals, ArrayList<ArrayList<String>> mEvents) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.mDistances = mDistance;
        this.mLocation= mLocation;
        this.mPace = mPace;
        this.mGoals = mGoals;
        this.mEvents = mEvents;
        this.mInfo = mInfo;
    }

    /**
     * Creates the vew holder of each partner
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * Binds the view holder of the partner with his information
     * @param holder
     * @param position
     */
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
                intent.putExtra("Profile name", mImageNames.get(position));
                intent.putExtra("location", mLocation.get(position));
                intent.putExtra("pace", mPace.get(position));
                intent.putExtra("distances", mDistances.get(position));
                intent.putExtra("goals", mGoals.get(position));
                intent.putExtra("events", mEvents.get(position));

                intent.putExtra("info", mInfo.get(position));

                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Gets the number of partners
     * @return
     */
    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    /**
     * Represents the view holder of each partner
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        /* The image of the partner */
        CircleImageView image;

        /* The image name of the partner */
        TextView imageName;

        /* The pace of the partner */
        TextView Pace;

        /* The location of the partner */
        TextView location;

        /* The layout of the partner */
        RelativeLayout parentLayout;

        /**
         * Creates a view holder object for the partner
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.partner_list_image);
            imageName=itemView.findViewById(R.id.partner_list_name);
//            Pace = itemView.findViewById(R.id.partner_list_pace);
            location = itemView.findViewById(R.id.partner_list_location);
            parentLayout=itemView.findViewById(R.id.parent_layout);


        }
    }

}