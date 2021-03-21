# YouTube API Android 11 (API 30)

This is a project that successfully runs the Youtube Player View using Android 11. This repo was repurposed for workshop events.

# App Creation
- Do NOT click on Legacy Libraries
- Any API (30 by default)
# Assets
In the zoom I will share a zip file of the assets we will use for our app.
[Google Drive Link](https://drive.google.com/drive/folders/1GdrsVoLbOwM0exTk7bx23ET76KmGsMsf?usp=sharing)
# Installations
 We **need** the [YouTube Android Player API](https://developers.google.com/youtube/android/player/downloads)
 
>To start, you will need to create an API key through https://console.developers.google.com/. Make sure to enable the YouTube Data API v3. Go to the Credentials section and generate an API key.
 

# Dependencies

In **build.gradle(Module)**
`implementation files('libs/YouTubeAndroidPlayerApi.jar')`

`    implementation group: 'commons-io', name: 'commons-io', version: '2.6'`
# Android Manifest
```
<!-- We need Internet Access -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- Networks State used to check connection -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> 

<!-- COMMENT OUT THE LINES BELOW FOR NON-ANDROID 11 APPS -->
<queries>
    <intent>
        <action android:name="com.google.android.youtube.api.service.START" />
    </intent>
</queries>
```
May need a useClearTextTraffic call if making a network call.
`android:usesCleartextTraffic="true"`

# Required Features
- [X] App correctly loads the Splash Screen
- [X] Implement the YouTube Player View
- [X] Cue/Load Videos
- [X] Change between Videos
- [X] Design the layout of the media viewer

# Steps
1. Create the Activities/Splash Screen layouts
2. Import all of our assets from the zip file as vector assets into the drawables folder
3. Begin writing the Splash Screen

```
// Make this activity, full screen
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
// Hide the Title bar of this activity screen
getWindow().requestFeature(Window.FEATURE_NO_TITLE);
setContentView(R.layout.activity_launch_splash);

final Handler handler = new Handler();
handler.postDelayed(new Runnable() {
    @Override
    public void run(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}, 2000L);
```
4. Create the YouTube Player
Above our class, we need to import YouTubeBaseActivity `import com.google.android.youtube.player.YouTubeBaseActivity;`
Also, make sure that our class extends the YoutubeBaseActivity
```
public class MainActivity extends YouTubeBaseActivity {

    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private YouTubePlayer mYouTubePlayer;
    private static final String TAG = "MainActivity";
    int IndexOfVideos = 0;
    ...
```


5. Outside of our `onCreate`, we will define a new method that will handle making a new instance of the player
```
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
```
1. Setup the logic for switching between videos
```
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
```


# Additional Features

Implement more features:
- [ ] Add a no network splashscreen(no wifi vector added in assets folder)
- [ ] Use local storage/room to store the video ids
- [ ] Create a new activity that allows you to query for videos to store
- [ ] If video fails to load, place an error video icon on the video

### Author
> Giancarlo or @ggiande on github!
>
> [My LinkedIn](https://www.linkedin.com/in/giancarlo-garcia/) - Let's connect! 
For questions feel free to email me at [ggarciadeleon@csustan.edu](mailto:ggarciadeleon@csustan.edu)
