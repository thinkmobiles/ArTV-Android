package com.artv.android.system.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.artv.android.R;
import com.artv.android.app.playback.IPlaybackController;
import com.artv.android.core.Constants;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.GlobalConfig;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.DbWorker;
import com.artv.android.system.custom_views.CustomMediaController;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misha on 6/30/2015.
 */
public final class MediaPlayerFragment extends BaseFragment implements IPlaybackController {

    private FrameLayout mVideoContainer;
    private LinearLayout mRightContainer, mBottomContainer;
    private TextView mRightText, mBottomText;
    private VideoView mVideoWindow;
    private ImageView ivImage;

    private SurfaceHolder mSurfaceHolder;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_media_player, _container, false);

        findViews(view);
        prepareVideoViews();

        return view;
    }

    private final void findViews(final View _view) {
        mVideoContainer = (FrameLayout) _view.findViewById(R.id.flVideoContainer_FMP);
        mBottomContainer = (LinearLayout) _view.findViewById(R.id.llBottomContainer_FMP);
        mRightContainer = (LinearLayout) _view.findViewById(R.id.llRightContainer_FMP);
        mRightText = (TextView) _view.findViewById(R.id.tvRightText_FMP);
        mBottomText = (TextView) _view.findViewById(R.id.tvBottomText_FMP);
        mVideoWindow = (VideoView) _view.findViewById(R.id.vvPlayerWindow_FMP);
        ivImage = (ImageView) _view.findViewById(R.id.ivImage_FMP);
    }

    private final void prepareVideoViews() {
        mSurfaceHolder = mVideoWindow.getHolder();
        final CustomMediaController mediaController = new CustomMediaController(getActivity());
        mediaController.setAnchorView(mVideoWindow);
        mVideoWindow.setMediaController(mediaController);
        mVideoWindow.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public final void onPrepared(final MediaPlayer _mp) {
                _mp.start();
            }
        });

        mediaController.setFullScreenBtnListener(new CustomMediaController.OnFullScreenBtnClickListener() {
            @Override
            public void onFullScreenBtnClicked(boolean isFullScreenMode) {
                if (isFullScreenMode) switchToFullScreenMode();
                else switchToBoxedMode();
            }
        });

        mVideoWindow.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public final void onCompletion(final MediaPlayer _mp) {

            }
        });

        mVideoWindow.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public final boolean onError(final MediaPlayer _mp, final int _what, final int _extra) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Error playing: what = " + _what + ", extra = " + _extra,
                        Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        if (_savedInstanceState == null) ;
    }

    @Override
    public final void onDestroy() {
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

    public void setMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        Picasso.with(getActivity()).load(_msgBoardCampaign.bottomBkgURL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mBottomContainer.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                mBottomContainer.setBackgroundDrawable(null);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mBottomContainer.setBackgroundDrawable(null);
            }
        });
        Picasso.with(getActivity()).load(_msgBoardCampaign.rightBkgURL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mRightContainer.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                mRightContainer.setBackgroundDrawable(null);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mRightContainer.setBackgroundDrawable(null);
            }
        });

        int textColor = Color.parseColor(_msgBoardCampaign.textColor);
        mBottomText.setTextColor(textColor);
        mRightText.setTextColor(textColor);

    }

    @Override
    public final void playLocalVideo(final String _path) {
        ivImage.setVisibility(View.INVISIBLE);
        mVideoWindow.setVideoPath(_path);
    }

    @Override
    public final void playLocalPicture(final String _path) {
        mVideoWindow.stopPlayback();
        ivImage.setVisibility(View.VISIBLE);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        try {
            ivImage.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(_path), null, options));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public final void playYoutubeLink(final String _url) {
        //todo: implement
    }

}
