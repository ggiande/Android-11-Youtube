package com.example.youtubeandroid11;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class MainActivity extends YouTubeBaseActivity {
    // inside of any of your application's code
    //private static final String YOUTUBE_API_KEY = BuildConfig.CONSUMER_KEY;
    private static final String YOUTUBE_API_KEY = "APIGOESHERE";
    public static final String TAG = "MainActivity";

    int IndexOfVideos = 0;
    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private YouTubePlayer mYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Constants
        ArrayList<String> videos = new ArrayList<>();
        videos.add("yPYZpwSpKmA");
        videos.add("dQw4w9WgXcQ");
        videos.add("gQfGgHfQgv0");      // Mashup ;)
        videos.add("Su6kidaGW_8");

        youTubePlayerSetup(videos.get(IndexOfVideos)); // Runs our code at least once

        findViewById(R.id.ic_left_arrow).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (videos.size() == 0)
                    return;

                IndexOfVideos--;
                // make sure we don't get an IndexOutOfBoundsError if we are viewing the first indexed video in our list
                if(IndexOfVideos < 0) {
                    IndexOfVideos = videos.size()-1;
                    mYouTubePlayer.cueVideo(videos.get(IndexOfVideos));
                }
                mYouTubePlayer.cueVideo(videos.get(IndexOfVideos));
            }
        });

        findViewById(R.id.ic_right_arrow).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (videos.size() == 0)
                    return;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed video in our list
                IndexOfVideos++;
                if(IndexOfVideos >= videos.size()) {
                    IndexOfVideos = 0;
                    mYouTubePlayer.cueVideo(videos.get(IndexOfVideos));
                } else {
                    mYouTubePlayer.cueVideo(videos.get(IndexOfVideos));
                }
            }
        });

    }

    private void youTubePlayerSetup(String videoID) {

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    mYouTubePlayer = youTubePlayer;
                    mYouTubePlayer.cueVideo(videoID);
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e(TAG, "Error on Initializing Youtube");
            }
        };
        mYouTubePlayerView.initialize(YOUTUBE_API_KEY, mOnInitializedListener);

    }
}