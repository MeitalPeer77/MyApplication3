
package com.example.myapplication3;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication3.R;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    // list of images
    public int[] lst_images = {
            R.drawable.netta,
            R.drawable.maayanm,
            R.drawable.yehonatans,
            R.drawable.noam,
            R.drawable.liors,
            R.drawable.toms,
            R.drawable.almoga,

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
    // list of background colors
//    public int[]  lst_backgroundcolor = {
//            Color.rgb(55,55,55),
//            Color.rgb(239,85,85),
//            Color.rgb(110,49,89),
//            Color.rgb(1,188,212)
//    };


    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_title.length;
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
//        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}