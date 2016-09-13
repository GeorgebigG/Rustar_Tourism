package com.rustaronline.mobile.rustartourism.YouTube;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rustaronline.mobile.rustartourism.Helper.Config;
import com.rustaronline.mobile.rustartourism.R;

public class VideoPlayer extends YouTubeBaseActivity {

    YouTubePlayerView playerView;
    YouTubePlayer.OnInitializedListener listener;

    public static final String VIDE_ID = "_videId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        if (getIntent() == null)
            return;

        final String videId = getIntent().getStringExtra(VIDE_ID);
        playerView = (YouTubePlayerView) findViewById(R.id.main_youtube_view);
        listener = new YouTubePlayer.OnInitializedListener() {

            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videId);
            }

            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {}
        };

        playerView.initialize(Config.API, listener);
    }
}
