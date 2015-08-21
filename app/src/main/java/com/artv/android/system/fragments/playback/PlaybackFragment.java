package com.artv.android.system.fragments.playback;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.artv.android.R;
import com.artv.android.app.message.IMessageController;
import com.artv.android.app.message.MessageWorker;
import com.artv.android.app.playback.IPlaybackController;
import com.artv.android.app.playback.IVideoCompletionListener;
import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.core.UrlHelper;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.system.ArTvApplication;
import com.artv.android.system.fragments.BaseFragment;
import com.artv.android.system.fragments.youtube.YoutubeVideoFragment;
import com.artv.android.system.fragments.youtube.YoutubeVideoListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * Created by Misha on 6/30/2015.
 */
public final class PlaybackFragment extends BaseFragment implements IPlaybackController, IMessageController {

    private RelativeLayout rlPlayContainer;
    private RelativeLayout rlVideo;
    private RelativeLayout rlImage;
    private ImageView ivImage;
    private ImageView ivBottomBg;
    private ImageView ivRightBg;
    private VideoView vvVideoPlayer;
    private RelativeLayout rlRightContainer;
    private RelativeLayout rlBottomContainer;
    private TextView tvRightText;
    private TextView tvBottomText;

    private PlaybackWorker mPlaybackWorker;
    private IVideoCompletionListener mVideoCompletionListener;
    private MessageWorker mMessageWorker;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        mPlaybackWorker = getApplicationLogic().getPlaybackWorker();
        mPlaybackWorker.setPlaybackController(this);
        mVideoCompletionListener = mPlaybackWorker.getVideoCompletionListener();

        mMessageWorker = getApplicationLogic().getMessageWorker();
        mMessageWorker.setMessageController(this);
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_playback, _container, false);

        findViews(view);
        prepareVideoViews();

        return view;
    }

    private final void findViews(final View _view) {
        rlPlayContainer = (RelativeLayout) _view.findViewById(R.id.rlPlayContainer_FP);
        rlVideo = (RelativeLayout) _view.findViewById(R.id.rlVideo_FP);
        rlImage = (RelativeLayout) _view.findViewById(R.id.rlImage_FP);
        ivImage = (ImageView) _view.findViewById(R.id.ivImage_FP);
        ivBottomBg = (ImageView) _view.findViewById(R.id.ivBottomBg_FP);
        ivRightBg = (ImageView) _view.findViewById(R.id.ivRightBg_FP);
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
                vvVideoPlayer.stopPlayback();
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
        if (_savedInstanceState == null) {
            mPlaybackWorker.startPlayback();
            mMessageWorker.playMessages();
        }
    }

    @Override
    public final void onStop() {
        super.onStop();
        mPlaybackWorker.stopPlayback();
        mMessageWorker.stopMessages();
    }

    //region playing assets
    @Override
    public final void playLocalVideo(final String _path) {
        setImageVisibility(false);
        setVideoVisibility(true);

        vvVideoPlayer.setVideoPath(_path);
    }

    @Override
    public final void playLocalPicture(final String _path) {
        setImageVisibility(true);
        setVideoVisibility(false);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        try {
            ivImage.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(_path), null, options));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private final void setImageVisibility(final boolean _visible) {
        rlImage.setVisibility(_visible ? View.VISIBLE : View.GONE);
    }

    private final void setVideoVisibility(final boolean _visible) {
        rlVideo.setVisibility(_visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public final void playYoutubeLink(final String _url) {
        final YoutubeVideoFragment fragment = YoutubeVideoFragment.newInstance(_url);
        fragment.setYoutubeVideoListener(new YoutubeVideoListener() {
            @Override
            public final void onVideoStarted() {

            }

            @Override
            public final void onVideoEnded() {
                getChildFragmentManager().beginTransaction().remove(fragment).commit();
                mVideoCompletionListener.onVideoCompleted();
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.rlPlayContainer_FP, fragment).commit();
    }
    //endregion

    @Override
    public final void showMessageUi() {
        rlRightContainer.setVisibility(View.VISIBLE);
        rlBottomContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public final void hideMessageUi() {
        rlRightContainer.setVisibility(View.GONE);
        rlBottomContainer.setVisibility(View.GONE);
    }

    @Override
    public final void setBottomBg(final String _url) {
        Picasso.with(getActivity()).load(_url).into(ivBottomBg);
    }

    @Override
    public final void setRightBg(final String _url) {
        Picasso.with(getActivity()).load(_url).into(ivRightBg);
    }

    @Override
    public final void setTextColor(final int _color) {
        tvBottomText.setTextColor(_color);
        tvRightText.setTextColor(_color);
    }

    @Override
    public final void showRightMessage(final String _message) {
        tvRightText.setText(_message);
    }

    @Override
    public final void showBottomMessage(final String _message) {
        tvBottomText.setText(_message);
    }
}
