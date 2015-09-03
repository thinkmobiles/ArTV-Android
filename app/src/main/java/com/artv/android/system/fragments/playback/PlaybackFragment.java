package com.artv.android.system.fragments.playback;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.artv.android.R;
import com.artv.android.app.beacon.BeaconScheduler;
import com.artv.android.app.message.IMessageController;
import com.artv.android.app.message.MessageWorker;
import com.artv.android.app.playback.IPlaybackController;
import com.artv.android.app.playback.IVideoCompletionListener;
import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.display.TurnOffWorker;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.log.ILogger;
import com.artv.android.database.DbWorker;
import com.artv.android.system.fragments.BaseFragment;
import com.artv.android.system.fragments.youtube.YoutubeVideoFragment;
import com.artv.android.system.fragments.youtube.YoutubeVideoListener;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Misha on 6/30/2015.
 */
public final class PlaybackFragment extends BaseFragment implements IPlaybackController, IMessageController, ILogger {

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
    private TextView tvLog;

    private PlaybackWorker mPlaybackWorker;
    private IVideoCompletionListener mVideoCompletionListener;
    private MessageWorker mMessageWorker;
    private BeaconScheduler mBeaconScheduler;
    private TurnOffWorker mTurnOffWorker;

    private ConfigInfo mConfigInfo;

    private DbWorker mDbWorker;

    private YoutubeVideoFragment mFragment;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        setHasOptionsMenu(true);

        mPlaybackWorker = getApplicationLogic().getPlaybackWorker();
        mPlaybackWorker.setPlaybackController(this);
        mPlaybackWorker.setDeviceAdministrator(getApplicationLogic().getDeviceAdministrator());
        mVideoCompletionListener = mPlaybackWorker.getVideoCompletionListener();

        mMessageWorker = getApplicationLogic().getMessageWorker();
        mMessageWorker.setMessageController(this);

        mBeaconScheduler = getApplicationLogic().getBeaconScheduler();
        mTurnOffWorker = getApplicationLogic().getTurnOffWorker();

        mConfigInfo = getApplicationLogic().getConfigInfoWorker().getConfigInfo();

        mDbWorker = getApplicationLogic().getDbWorker();
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_playback, _container, false);

        findViews(view);
        prepareViews();
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
        tvLog = (TextView) _view.findViewById(R.id.tvLog_FP);
    }

    private final void prepareViews() {
        tvLog.setVisibility(mConfigInfo.getShowDebugInfo() ? View.VISIBLE : View.GONE);
        tvLog.setMovementMethod(new ScrollingMovementMethod());
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
            mBeaconScheduler.startSchedule();
            mTurnOffWorker.turnOff();
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        ArTvLogger.addLogger(this);
    }

    @Override
    public final void onStop() {
        super.onStop();
        ArTvLogger.removeLogger(this);
        mPlaybackWorker.stopPlayback();
        mMessageWorker.stopMessages();
    }

    @Override
    public final void onCreateOptionsMenu(final Menu _menu, final MenuInflater _inflater) {
        _inflater.inflate(R.menu.menu_main, _menu);
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem _item) {
        switch (_item.getItemId()) {
            case R.id.stopAllAndClearDb_MM:
                onClickStopAllAndClearDb();
                return true;
        }

        return super.onOptionsItemSelected(_item);
    }

    private final void onClickStopAllAndClearDb() {
        ArTvLogger.removeLogger(this);
        mPlaybackWorker.stopPlayback();
        mMessageWorker.stopMessages();
        mBeaconScheduler.stopSchedule();
        mTurnOffWorker.cancel();
        mDbWorker.drop();
        getActivity().finish();
    }

    //region playing assets
    @Override
    public final void playLocalVideo(final String _path) {
        setImageVisibility(false);
        setVideoVisibility(true);
        vvVideoPlayer.setVisibility(View.VISIBLE);
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
        setVideoVisibility(false);
        setImageVisibility(false);
        vvVideoPlayer.setVisibility(View.GONE);
        mFragment = YoutubeVideoFragment.newInstance(_url);
        mFragment.setYoutubeVideoListener(new YoutubeVideoListener() {
            @Override
            public final void onVideoStarted() {

            }

            @Override
            public final void onVideoEnded() {
                getChildFragmentManager().beginTransaction().remove(mFragment).commit();
                mVideoCompletionListener.onVideoCompleted();
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.rlPlayContainer_FP, mFragment).commit();
    }

    @Override
    public void stopPlaying() {
        if(vvVideoPlayer != null && vvVideoPlayer.isPlaying()) {
            vvVideoPlayer.stopPlayback();
            vvVideoPlayer.setVisibility(View.GONE);
        } else if (mFragment != null) {
            getChildFragmentManager().beginTransaction().remove(mFragment).commitAllowingStateLoss();
            mFragment = null;
        } else {
            setImageVisibility(false);
        }
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

    @Override
    public final void printMessage(final String _message) {
        printMessage(true, _message);
    }

    @Override
    public final void printMessage(boolean _fromNewLine, String _message) {
        tvLog.append((_fromNewLine ? "\n " : "") + _message);
    }
}
