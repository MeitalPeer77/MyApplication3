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

/**
 * The adapter of event screen
 */
public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder>{

    /* A list of the names of the events */
    private ArrayList<String> mImageName = new ArrayList<>();

    /* A list of the images of the events */
    private ArrayList<String> mImages = new ArrayList<>();

    /* A list of the details of the events */
    private ArrayList<String> mDetails = new ArrayList<>();

    /* A list of the sign uo urls of the events */
    private ArrayList<Integer> mSignUp = new ArrayList<>();

    /* The context of the activity */
    private Context mContext;

    /**
     * Creates a EventRecyclerAdapter object
     * @param mContext
     * @param mImageName
     * @param mImages
     * @param mEventDetails
     * @param mRegister
     */
    public EventRecyclerAdapter(Context mContext, ArrayList<String> mImageName,
                                ArrayList<String> mImages, ArrayList<String> mEventDetails,
                                ArrayList<Integer> mRegister) {
        this.mImageName = mImageName;
        this.mImages = mImages;
        this.mDetails = mEventDetails;
        this.mSignUp = mRegister;
        this.mContext = mContext;


    }

    /**
     * Creates the view holder of the event
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * Binds the event information to the event view holder
     * @param holder
     * @param position
     */
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

    /**
     * Gets the number of events
     * @return
     */
    @Override
    public int getItemCount() {
        return mImageName.size();
    }

    /**
     * Class represents the view holder of event
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        /* The circled image view of the event */
        CircleImageView image;

        /* The name of the event's image */
        TextView imageName;

        /* The details of the event */
        TextView eventDetails;

        /* The relative layout of the events*/
        RelativeLayout parentLayout;

        /* The sign up to event text*/
        TextView sighUpText;

        /**
         * Creates the view holder for the event
         * @param itemView
         */
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