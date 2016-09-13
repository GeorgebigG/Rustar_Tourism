package com.rustaronline.mobile.rustartourism.YouTube;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rustaronline.mobile.rustartourism.Helper.Config;
import com.rustaronline.mobile.rustartourism.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YouTube extends YouTubeBaseActivity {

    LinearLayout youtubeLayout;
    ArrayList<String> videoIdes;

    public static final int AMOUNT_OF_VIDEOS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        ProgressBar[] bars = new ProgressBar[AMOUNT_OF_VIDEOS];
        for (int i = 0; i < bars.length; i++) bars[i] = new ProgressBar(this);

        ImageView[] images = new ImageView[AMOUNT_OF_VIDEOS];
        for (int i = 0; i < images.length; i++) images[i] = new ImageView(this);

        youtubeLayout = (LinearLayout) findViewById(R.id.youtube_layout);

        videoIdes = new ArrayList<String>();
        videoIdes.add("Ny0PSboZSdw");
        videoIdes.add("PuKWn-u3K_0");
        videoIdes.add("xL7OQ_PIg6U");

        TextView text;
        LinearLayout.LayoutParams lParams;

        for (int i = 0; i < AMOUNT_OF_VIDEOS; i++) {
            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.gravity = Gravity.CENTER;
            lParams.topMargin = 5;
            youtubeLayout.addView(bars[i], lParams);

            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.topMargin = 5;
            images[i].setScaleType(ImageView.ScaleType.FIT_XY);
            images[i].setClickable(true);
            final Intent intent = new Intent(this, VideoPlayer.class);
            final int index = i;
            final String videId = videoIdes.get(i);
            images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra(VideoPlayer.VIDE_ID, videId);
                    startActivity(intent);
                }
            });
            youtubeLayout.addView(images[i], lParams);

            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.gravity = Gravity.CENTER;
            text = new TextView(this);
            lParams.bottomMargin = 15;
            text.setText("Diary of a Wimpy Kid (" + (i+1) + ")");
            text.setTextColor(getResources().getColor(android.R.color.black));
            youtubeLayout.addView(text, lParams);
        }

        new YouTubeImage("https://i.ytimg.com/vi/_OtVh2XpiRk/hqdefault.jpg?custom=true&w=480&h=320&jpg444=true&jpgq=90&sp=68&sigh=57o1o367_a5JR_owuQH5ET5xYrs",
                bars[0], images[0], youtubeLayout, this).execute();

        new YouTubeImage("https://i.ytimg.com/vi/H3JOeW8Q_wU/hqdefault.jpg?custom=true&w=480&h=320&jpg444=true&jpgq=90&sp=68&sigh=gVP6u7FRhXx81JunNEaFdyZMfKI",
                bars[1], images[1], youtubeLayout, this).execute();

        new YouTubeImage("https://i.ytimg.com/vi/pHZC5ghN_gU/hqdefault.jpg?custom=true&w=480&h=320&jpg444=true&jpgq=90&sp=68&sigh=Gd6UpmJ23tv3C7ncsErgxq_yBuA",
                bars[2], images[2], youtubeLayout, this).execute();
    }

/*
https://i.ytimg.com/vi/_OtVh2XpiRk/hqdefault.jpg?custom=true&w=320&h=180&stc=true&jpg444=true&jpgq=90&sp=68&sigh=iRJ4IKNHTvsYN72MBGWxteS0Psw
https://i.ytimg.com/vi/_OtVh2XpiRk/hqdefault.jpg?custom=true&w=120&h=90&jpg444=true&jpgq=90&sp=68&sigh=57o1o367_a5JR_owuQH5ET5xYrs
https://i.ytimg.com/vi/_OtVh2XpiRk/hqdefault.jpg?custom=true&w=480&h=320&jpg444=true&jpgq=90&sp=68&sigh=57o1o367_a5JR_owuQH5ET5xYrs



https://i.ytimg.com/vi/H3JOeW8Q_wU/hqdefault.jpg?custom=true&w=120&h=90&jpg444=true&jpgq=90&sp=68&sigh=gVP6u7FRhXx81JunNEaFdyZMfKI
*/
}
