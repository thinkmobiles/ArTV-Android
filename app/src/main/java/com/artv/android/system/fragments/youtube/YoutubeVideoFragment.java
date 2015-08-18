package com.artv.android.system.fragments.youtube;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.artv.android.R;
import com.artv.android.system.fragments.BaseFragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by
 * mRogach on 14.08.2015.
 */
public class YoutubeVideoFragment extends BaseFragment implements YouTubePlayer.PlayerStateChangeListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private static final String YoutubeDeveloperKey = "AIzaSyCZe0fNjL91GSTJZh5Bq0x7g3KHKF6lSoE";
    private String mYoutubeUrl;
    private String mYoutubeVideoID;
    private YouTubePlayer mYouTubePlayer;
    private YoutubeVideoListener youtubeVideoListener;

    public static YoutubeVideoFragment newInstance(String _youtubeUrl) {
        Bundle args = new Bundle();
        args.putString("youtube_url", _youtubeUrl);
        YoutubeVideoFragment fragment = new YoutubeVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final View view = layoutInflater.inflate(R.layout.fragment_youtube_video_player, viewGroup, false);

        getUrlArguments();
        mYoutubeVideoID = getVideoNameFromUrl(mYoutubeUrl);
        showFragment();
        initFragment();
        Log.v("youtube", getVideoNameFromUrl(mYoutubeUrl));
        return view;
    }

    private void getUrlArguments() {
        if (getArguments().getString("youtube_url") != null)
            mYoutubeUrl = getArguments().getString("youtube_url");
    }

    private void showFragment() {
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
    }

    private String getVideoNameFromUrl(final String _youtubeVideoUrl) {
        if (!_youtubeVideoUrl.isEmpty()) {
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(_youtubeVideoUrl);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return null;
    }

    private void initFragment() {
        youTubePlayerFragment.initialize(YoutubeDeveloperKey, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

                if (!wasRestored) {
                    mYouTubePlayer = youTubePlayer;
                    mYouTubePlayer.setPlayerStateChangeListener(YoutubeVideoFragment.this);
                    mYouTubePlayer.loadVideo(mYoutubeVideoID);
                    mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
                } else {
                    String errorMessage = String.format(
                            getString(R.string.error_player), youTubeInitializationResult.toString());
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setYoutubeVideoListener (YoutubeVideoListener youtubeVideoListener) {
        this.youtubeVideoListener = youtubeVideoListener;
    }

    private YoutubeVideoController youtubeVideoController = new YoutubeVideoController() {
        @Override
        public void play() {
            if (mYouTubePlayer != null && !mYouTubePlayer.isPlaying()) {
                mYouTubePlayer.play();
            }
        }

        @Override
        public void stop() {
            if (mYouTubePlayer != null && mYouTubePlayer.isPlaying()) {
                mYouTubePlayer.release();
            }
        }
    };

    public YoutubeVideoController getYoutubeVideoController() {
        return youtubeVideoController;
    }

    @Override
    public void onLoading() {
        Log.v("video", "onLoading");
    }

    @Override
    public void onLoaded(String s) {
        Log.v("video", "onLoaded");
    }

    @Override
    public void onAdStarted() {
        Log.v("video", "onAdStarted");
    }

    @Override
    public void onVideoStarted() {
        Log.v("video", "onVideoStarted");
        youtubeVideoListener.onVideoStarted();
    }

    @Override
    public void onVideoEnded() {
        Log.v("video", "onVideoEnded");
        youtubeVideoListener.onVideoEnded();
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        Log.v("video", "onError");
    }
}
