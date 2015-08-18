package com.artv.android.app.playback;

/**
 * Created by ZOG on 8/18/2015.
 */
public final class PlaybackWorker implements IVideoCompletionListener {

    private IPlaybackController mPlaybackController;

    public final void setPlaybackController(final IPlaybackController _controller) {
        mPlaybackController = _controller;
    }

    public final IVideoCompletionListener getVideoCompletionListener() {
        return this;
    }

    public final void startPlayback() {

    }

    public final void stopPlayback() {
        
    }

    @Override
    public final void onVideoCompleted() {
        //play next
    }
}
