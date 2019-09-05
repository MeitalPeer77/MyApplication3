package com.example.RunningMatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 ** Represents the adapter of events in Profile Gallery
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    /* screen context */
    private Context mContext;
    /* a list of all events */
    private ArrayList<String> events;


    /**
     * Creates an event adapter
     * @param context
     * @param events
     */
    public EventAdapter(Context context, ArrayList<String> events) {
        this.mContext = context;
        this.events = events;
    }

    /*
     * Creates a list of all the event's URLs
     */
    private ArrayList<String> eventsList() {

        ArrayList<String> urls = new ArrayList<>();
            if(events.size()==1& events.contains("check")) {
                urls.add("no upcoming events");
                return urls;
            }
        for (String event : events) {
            switch (event) {
                case "Golan Race":
                    urls.add("https://i.ytimg.com/vi/vkP2kygkl6c/hqdefault.jpg");
                    break;
                case "Night Run":
                    urls.add("http://www.biblemarathon.co.il/webfiles/Gallery/3/815/bible2016run.JPG");
                    break;
                case "Running in Work":
                    urls.add("https://i.ytimg.com/vi/vkP2kygkl6c/hqdefault.jpg");
                    break;
                case "Running With Shahar":
                    urls.add("\"https://cdn.isnet.co.il/dyncontent/tmp/267/2018_5_14_fb9e3423-12df-4f52-af78-5250ae9c5876_510_1000_Fit_.png\"");
                    break;

                case "Half Marathon":
                    urls.add("\"https://www.shvoong.co.il/wp-content/uploads/2014/10/52151-300x1992.jpg\"");
                    break;

                case "Nachal Race":
                    urls.add("\"https://www.shvoong.co.il/wp-content/uploads/2017/08/Nahal_july.png");
                    break;

                case "Megido Race":
                    urls.add("\"https://www.shvoong.co.il/wp-content/uploads/2017/08/Nahal_july.png");
                    break;
            }
        }
        return urls;
    }


    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_icon, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder viewHolder, int i) {
        ArrayList<String> mImages = eventsList();
        if (!mImages.isEmpty()) {
            Glide.with(mContext).load(mImages.get(i)).into(viewHolder.image);
            viewHolder.eventName.setText(events.get(i+1));
        }else{
            Glide.with(mContext)
                    .asBitmap()
                    .load(R.drawable.enmpty_drawable)
                    .into(viewHolder.image);
        }
    }


    @Override
    public int getItemCount() {
        return events.size()-1;
    }

    /**
     * Represents a view holder of the event
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView eventName;
        TextView noEvent;


        /**
         * Creates a view holder of the event
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            eventName = itemView.findViewById(R.id.event_text);
            noEvent = itemView.findViewById(R.id.input_no_events);
        }

    }
}
