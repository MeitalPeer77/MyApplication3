package com.example.RunningMatch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> events;


    public EventAdapter(Context context, ArrayList<String> events) {
        this.mContext = context;
        this.events = events;
    }

    private ArrayList<String> eventsList() {
        ArrayList<String> urls = new ArrayList<>();
        for (String event : events) {
            switch (event) {
                case "Golan race":
                    urls.add("http://www.winning.co.il/events/2019/golan/header.png");
                    break;
                case "Night Run":
                    urls.add("http://www.winning.co.il/events/2019/kiryat-gat/header.png");
                    break;
                case "Running in Work":
                    urls.add("http://liga.org.il/wp-content/uploads/2017/12/liga-logo-200.jpg");
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ArrayList<String> mImages = eventsList();
        if (!mImages.isEmpty()) {
            Glide.with(mContext).load(mImages.get(i)).into(viewHolder.image);
            viewHolder.text.setText(events.get(i+1));
        }
    }


    @Override
    public int getItemCount() {
        return events.size()-1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            text = itemView.findViewById(R.id.event_text);

        }

    }
}
