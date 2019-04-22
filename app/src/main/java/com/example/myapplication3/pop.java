package com.example.myapplication3;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = (int) (dm.widthPixels*0.5);
        int high = (int) (dm.heightPixels*0.5);

        getWindow().setLayout(width, high);
    }
}
