package com.artv.android.app.playback_loop;

import com.artv.android.app.beacon.BeaconScheduler;
import com.artv.android.app.message.MessageWorker;
import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.core.display.TurnOffWorker;

/**
 * Created by ZOG on 9/17/2015.
 */
public final class PlaybackLoopController {

    private PlaybackWorker mPlaybackWorker;
    private MessageWorker mMessageWorker;
    private BeaconScheduler mBeaconScheduler;
    private TurnOffWorker mTurnOffWorker;

    private boolean mLooping = false;
    private boolean mUiAttached = false;

    public final void setPlaybackWorker(final PlaybackWorker _worker) {
        mPlaybackWorker = _worker;
    }

    public final void setMessageWorker(final MessageWorker _messageWorker) {
        mMessageWorker = _messageWorker;
    }

    public final void setTurnOffWorker(final TurnOffWorker _worker) {
        mTurnOffWorker = _worker;
    }

    public final void setBeaconScheduler(final BeaconScheduler _scheduler) {
        mBeaconScheduler = _scheduler;
    }

    public final boolean isLooping() {
        return mLooping;
    }

    public final void start() {
        mLooping = true;
        mBeaconScheduler.startSchedule();
        mTurnOffWorker.turnOff();
    }

    public final void stop() {
        mLooping = false;
        mBeaconScheduler.stopSchedule();
        mTurnOffWorker.cancel();
    }

    public final void attachUi() {
        mUiAttached = true;
        startUiPlayback();
    }

    public final void detachUi() {
        mUiAttached = false;
        stopUiPlayback();
    }

    private final void startUiPlayback() {
        mPlaybackWorker.startPlayback();
        mMessageWorker.playMessages();
    }

    private final void stopUiPlayback() {
        mPlaybackWorker.stopPlayback();
        mMessageWorker.stopMessages();
    }

}
