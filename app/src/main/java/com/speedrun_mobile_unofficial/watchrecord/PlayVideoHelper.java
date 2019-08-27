package com.speedrun_mobile_unofficial.watchrecord;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.entities.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayVideoHelper {

    public static void playVideo(Activity activity, String link) {
        String youtubeVideoId = extractYoutubeVideoIdFromLink(link);
        if(!youtubeVideoId.equals("")) {
            playYoutubeVideo(activity, youtubeVideoId);
            return;
        }
        displayFailureHint(activity);
    }

    public static String extractYoutubeVideoIdFromLink(String link) {
        System.out.println(link);
        if(link.length() == 11) {
            return link;
        }

        if(!link.contains("youtu")) {
            return "";
        }

        Pattern p1 = Pattern.compile("(?<=v=).*?(?=&|$)", Pattern.CASE_INSENSITIVE);
        Matcher m1 = p1.matcher(link);
        if(m1.find()){
            return m1.group();
        }

        Pattern p2 = Pattern.compile("(?<=youtu.be/).*", Pattern.CASE_INSENSITIVE);
        Matcher m2 = p2.matcher(link);
        if(m2.find()){
            return m2.group();
        }

        return "";
    }

    public static void playYoutubeVideo(Activity activity, String id) {
        YouTubePlayerFragment playerFragment = (YouTubePlayerFragment) activity.getFragmentManager().findFragmentById(R.id.youtube_fragment);
        playerFragment.initialize(Constants.YOUTUBE_DATA_API_V3_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(id);
                youTubePlayer.loadVideo(id);
//                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    private static void displayFailureHint(Activity activity) {
        activity.findViewById(R.id.watch_run_failure_hint).setVisibility(TextView.VISIBLE);
        activity.findViewById(R.id.watch_run_in_browser_btn).setVisibility(Button.VISIBLE);
    }
}
