package com.artv.android.system.fragments.playback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.artv.android.R;
import com.artv.android.app.playback.IPlaybackController;
import com.artv.android.app.playback.IVideoCompletionListener;
import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.DbWorker;
import com.artv.android.system.fragments.BaseFragment;
import com.artv.android.system.fragments.youtube.YoutubeVideoFragment;
import com.artv.android.system.fragments.youtube.YoutubeVideoListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Misha on 6/30/2015.
 */
public final class PlaybackFragment extends BaseFragment implements IPlaybackController {

    private FrameLayout flPlayContainer;
    private ImageView ivImage;
    private VideoView vvVideoPlayer;
    private RelativeLayout rlRightContainer;
    private RelativeLayout rlBottomContainer;
    private TextView tvRightText;
    private TextView tvBottomText;

    private PlaybackWorker mPlaybackWorker;
    private IVideoCompletionListener mVideoCompletionListener;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mPlaybackWorker = getApplicationLogic().getPlaybackWorker();
        mPlaybackWorker.setPlaybackController(this);
        mVideoCompletionListener = mPlaybackWorker.getVideoCompletionListener();
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_playback, _container, false);

        findViews(view);
        prepareVideoViews();

        return view;
    }

    private final void findViews(final View _view) {
        flPlayContainer = (FrameLayout) _view.findViewById(R.id.flPlayContainer_FP);
        ivImage = (ImageView) _view.findViewById(R.id.ivImage_FP);
        vvVideoPlayer = (VideoView) _view.findViewById(R.id.vvVideoPlayer_FP);
        rlRightContainer = (RelativeLayout) _view.findViewById(R.id.rlRightContainer_FP);
        rlBottomContainer = (RelativeLayout) _view.findViewById(R.id.rlBottomContainer_FP);
        tvRightText = (TextView) _view.findViewById(R.id.tvRightText_FP);
        tvBottomText = (TextView) _view.findViewById(R.id.tvBottomText_FP);
    }

    private final void prepareVideoViews() {
        vvVideoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public final void onPrepared(final MediaPlayer _mp) {
                _mp.start();
            }
        });

        vvVideoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public final void onCompletion(final MediaPlayer _mp) {
                mVideoCompletionListener.onVideoCompleted();
            }
        });

        vvVideoPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public final boolean onError(final MediaPlayer _mp, final int _what, final int _extra) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Error playing: what = " + _what + ", extra = " + _extra,
                        Toast.LENGTH_SHORT).show();
                mVideoCompletionListener.onVideoCompleted();
                return true;
            }
        });
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        if (_savedInstanceState == null) mPlaybackWorker.startPlayback();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
    }

    private void toggleMsgUi(final boolean _hasMsg) {
        rlRightContainer.setVisibility(_hasMsg ? View.VISIBLE : View.GONE);
        rlBottomContainer.setVisibility(_hasMsg ? View.VISIBLE : View.GONE);
    }

    private final void setMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        Picasso.with(getActivity()).load(_msgBoardCampaign.bottomBkgURL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                rlBottomContainer.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                rlBottomContainer.setBackgroundDrawable(null);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                rlBottomContainer.setBackgroundDrawable(null);
            }
        });
        Picasso.with(getActivity()).load(_msgBoardCampaign.rightBkgURL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                rlRightContainer.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                rlRightContainer.setBackgroundDrawable(null);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                rlRightContainer.setBackgroundDrawable(null);
            }
        });

        int textColor = Color.parseColor(_msgBoardCampaign.textColor);
        tvBottomText.setTextColor(textColor);
        tvRightText.setTextColor(textColor);

    }

    @Override
    public final void playLocalVideo(final String _path) {
        removeFragmentIfExist();
        ivImage.setVisibility(View.INVISIBLE);

        vvVideoPlayer.setVisibility(View.VISIBLE);
        vvVideoPlayer.setVideoPath(_path);
    }

    @Override
    public final void playLocalPicture(final String _path) {
        removeFragmentIfExist();
        vvVideoPlayer.stopPlayback();
        vvVideoPlayer.setVisibility(View.INVISIBLE);

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
        ivImage.setVisibility(View.INVISIBLE);
        vvVideoPlayer.stopPlayback();
        vvVideoPlayer.setVisibility(View.INVISIBLE);

        final YoutubeVideoFragment fragment = YoutubeVideoFragment.newInstance(_url);
        fragment.setYoutubeVideoListener(new YoutubeVideoListener() {
            @Override
            public final void onVideoStarted() {

            }

            @Override
            public final void onVideoEnded() {
                mVideoCompletionListener.onVideoCompleted();
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.flPlayContainer_FP, fragment).commit();
    }

    private final void removeFragmentIfExist() {
        final Fragment fragment = getChildFragmentManager().findFragmentById(R.id.flPlayContainer_FP);
        if (fragment != null) getChildFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public final void showMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        toggleMsgUi(_msgBoardCampaign != null);
        if (_msgBoardCampaign != null) setMsgBoardCampaign(_msgBoardCampaign);
    }

}
