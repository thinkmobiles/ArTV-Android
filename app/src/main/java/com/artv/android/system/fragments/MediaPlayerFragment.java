package com.artv.android.system.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.artv.android.R;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.system.custom_views.CustomMediaController;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Misha on 6/30/2015.
 */
public class MediaPlayerFragment extends Fragment {
    private FrameLayout mVideoContainer;
    private LinearLayout mRightContainer, mBottomContainer;
    private TextView mRightText, mBottomText;
    private VideoView mVideoWindow;

    private SurfaceHolder mSurfaceHolder;


    private static final String videoDirName = "artv";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_player, null);

        mVideoContainer = (FrameLayout) view.findViewById(R.id.flVideoContainer_FMP);
        mBottomContainer = (LinearLayout) view.findViewById(R.id.llBottomContainer_FMP);
        mRightContainer = (LinearLayout) view.findViewById(R.id.llRightContainer_FMP);

        mRightText = (TextView) view.findViewById(R.id.tvRightText_FMP);
        mBottomText = (TextView) view.findViewById(R.id.tvBottomText_FMP);


        mVideoWindow = (VideoView) view.findViewById(R.id.vvPlayerWindow_FMP);
        mSurfaceHolder = mVideoWindow.getHolder();


        CustomMediaController mediaController = new CustomMediaController(getActivity());
        mediaController.setAnchorView(mVideoWindow);
        mVideoWindow.setMediaController(mediaController);
        mVideoWindow.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
            }
        });

        Uri video = Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                + R.raw.bear_and_pool);
        mVideoWindow.setVideoURI(video);

        mediaController.setFullScreenBtnListener(new CustomMediaController.OnFullScreenBtnClickListener() {
            @Override
            public void onFullScreenBtnClicked(boolean isFullScreenMode) {
                if(isFullScreenMode) switchToFullScreenMode();
                else switchToBoxedMode();
            }
        });

        return view;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void switchToFullScreenMode() {
        mRightContainer.setVisibility(View.GONE);
        mBottomContainer.setVisibility(View.GONE);
    }

    private void switchToBoxedMode() {
        mRightContainer.setVisibility(View.VISIBLE);
        mBottomContainer.setVisibility(View.VISIBLE);
    }

    public void setMsgBoardCampaign(MsgBoardCampaign _msgBoardCampaign) {
        Picasso.with(getActivity()).load(_msgBoardCampaign.mBottomBkgURL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mBottomContainer.setBackground(new BitmapDrawable(getResources(),bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                mBottomContainer.setBackground(null);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mBottomContainer.setBackground(null);
            }
        });
        Picasso.with(getActivity()).load(_msgBoardCampaign.mRightBkgURL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mRightContainer.setBackground(new BitmapDrawable(getResources(),bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                mRightContainer.setBackground(null);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mRightContainer.setBackground(null);
            }
        });

        int textColor = Color.parseColor(_msgBoardCampaign.mTextColor);
        mBottomText.setTextColor(textColor);
        mRightText.setTextColor(textColor);

    }

}
