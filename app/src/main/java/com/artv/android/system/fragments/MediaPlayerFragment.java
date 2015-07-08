package com.artv.android.system.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.artv.android.R;
import com.artv.android.system.MainActivity;


/**
 * Created by Misha on 6/30/2015.
 */
public class MediaPlayerFragment extends Fragment {
    private FrameLayout mVideoContainer;
    private LinearLayout mRightContainer, mBottomContainer;
    private MediaController mediaControls;
    private VideoView mVideoView;
    private boolean isFullScreenMode;
    private MainActivity mMainActivity;


    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mMainActivity = (MainActivity) _activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMediaController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_player, null);
        findViews(view);
        setConfigVideoView();

        //todo this is just for sample
        mVideoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreenMode) switchToBoxedMode();
                else switchToFullScreenMode();
            }
        });




        return view;
    }

    private void switchToFullScreenMode() {
        isFullScreenMode = true;
        mRightContainer.setVisibility(View.GONE);
        mBottomContainer.setVisibility(View.GONE);
    }

    private void switchToBoxedMode() {
        isFullScreenMode = false;
        mRightContainer.setVisibility(View.VISIBLE);
        mBottomContainer.setVisibility(View.VISIBLE);
    }

    private void initMediaController() {
        if (mediaControls == null) {
            mediaControls = new MediaController(mMainActivity);
        }
    }

    private void findViews(View _view) {
        mVideoContainer = (FrameLayout) _view.findViewById(R.id.flVideoContainer_FMP);
        mBottomContainer = (LinearLayout) _view.findViewById(R.id.llBottomContainer_FMP);
        mRightContainer = (LinearLayout) _view.findViewById(R.id.llRightContainer_FMP);
        mVideoView = (VideoView) _view.findViewById(R.id.video_view);
    }

    private void setConfigVideoView() {

//        mVideoView.setVideoURI(Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp"));
        //        mVideoView.setVideoURI(Uri.parse("http://tvm.hasbrain.ru/api/GetCampaign.xml/uploads/1_Asset_LOGO.mov"));
        mVideoView.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/testVideo.mp4"));
//        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/" + "AndroidCommercial" + ".3gp");
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer arg0) {
                mVideoView.start();

            }
        });
    }

}
