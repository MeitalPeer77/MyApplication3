
package com.example.RunningMatch;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.RunningMatch.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<User> users;

    // list of images
    public int[] lst_images = {
            R.drawable.netta,
            R.drawable.maayanm,
            R.drawable.yehonatans,
            R.drawable.noam,
            R.drawable.liors,
            R.drawable.toms,
            R.drawable.almoga, R.drawable.almoga, R.drawable.almoga

    };

    // list of titles
    public String[] lst_title = {
            "Netta Zohar",
            "Maayan Yossef Magenheim",
            "Jehonathan Spigelman",
            "Noa Menasheof",
            "Lior Saadon",
            "Tom Saltsberg",
            "Almog Argaman"

    }   ;
    // list of descriptions
    public String[] lst_description = {
            "Looking for a running partner who will keep me motivated!",
            "I just want to stay in shape with short runs once a week.",
            "Running the marathon next year and need a partner to train with.",
            "Looking for a running partner. Twice a week. Early mornings.",
            "I like running at night. 10pm at the earliest.",
            "I hate running but want to stay in shape. Help me do this!",
            "I'm a serious sprinter. Catch me if you can!",
            

    };


    public SlideAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size()-1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card,container,false);

        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = (ImageView)  view.findViewById(R.id.slideimg);
        TextView txttitle= (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);

        String userName = users.get(position).getUserName();
        String des = users.get(position).getUserDescription();

        imgslide.setImageResource(R.drawable.yehonatans);
        txttitle.setText(userName);
        description.setText(des);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}