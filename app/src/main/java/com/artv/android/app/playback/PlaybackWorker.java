package com.artv.android.app.playback;

import android.os.Handler;

import com.artv.android.core.Constants;
import com.artv.android.database.DbWorker;

/**
 * Created by ZOG on 8/18/2015.
 */
public final class PlaybackWorker implements IVideoCompletionListener {

    private DbWorker mDbWorker;
    private IPlaybackController mPlaybackController;

    public final void setPlaybackController(final IPlaybackController _controller) {
        mPlaybackController = _controller;
    }

    public final IVideoCompletionListener getVideoCompletionListener() {
        return this;
    }

    public final void startPlayback() {
        mPlaybackController.playLocalPicture(Constants.PATH + "/2.jpg");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPlaybackController.playYoutubeLink("https://www.youtube.com/watch?v=dB0D-Egq46M");
            }
        }, 5000);
    }

    public final void stopPlayback() {
        
    }

    @Override
    public final void onVideoCompleted() {
        mPlaybackController.playLocalVideo(Constants.PATH + "/1.mp4");
    }
}
