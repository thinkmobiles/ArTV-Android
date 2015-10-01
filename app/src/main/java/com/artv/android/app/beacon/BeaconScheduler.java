package com.artv.android.app.beacon;

import android.os.Handler;

import com.artv.android.ArTvResult;
import com.artv.android.app.message.MessageWorker;
import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.core.Constants;
import com.artv.android.core.beacon.BeaconResult;
import com.artv.android.core.beacon.BeaconWorker;
import com.artv.android.core.beacon.IBeaconCallback;
import com.artv.android.core.campaign.CampaignResult;
import com.artv.android.core.campaign.CampaignWorker;
import com.artv.android.core.campaign.ICampaignCallback;
import com.artv.android.core.campaign.ICampaignDownloadListener;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.GlobalConfig;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.DbWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZOG on 8/27/2015.
 */
public final class BeaconScheduler {

    private BeaconWorker mBeaconWorker;
    private DbWorker mDbWorker;
    private MessageWorker mMessageWorker;
    private CampaignWorker mCampaignWorker;
    private PlaybackWorker mPlaybackWorker;

    private GlobalConfig mGlobalConfig;

    private Handler mHandler;
    private boolean mScheduling = false;

    public final void setBeaconWorker(final BeaconWorker _beaconWorker) {
        mBeaconWorker = _beaconWorker;
    }

    public final void setGlobalConfig(final GlobalConfig _globalConfig) {
        mGlobalConfig = _globalConfig;
    }

    public final void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final void setMessageWorker(final MessageWorker _messageWorker) {
        mMessageWorker = _messageWorker;
    }

    public final void setCampaignsWorker(final CampaignWorker _worker) {
        mCampaignWorker = _worker;
    }

    public final void setPlaybackWorker(final PlaybackWorker _worker) {
        mPlaybackWorker = _worker;
    }

    public final void startSchedule() {
        ArTvLogger.printMessage("Started beacon schedule");
        mScheduling = true;
        createHandler();
        doBeacon();
    }

    private final void doBeacon() {
        if (mScheduling) mBeaconWorker.doBeacon(mBeaconCallback);
    }

    public final void stopSchedule() {
        ArTvLogger.printMessage("Stopped beacon schedule");
        mScheduling = false;
        mCampaignWorker.cancelLoading();
        mHandler.removeCallbacks(delay);
    }

    private final void createHandler() {
        if (mHandler == null) mHandler = new Handler();
    }

    private final void startWithDelay(final long _delay) {
        ArTvLogger.printMessage(String.format("Next beacon in %d ms", _delay));
        mHandler.postDelayed(delay, _delay);
    }

    private final Runnable delay = new Runnable() {
        @Override
        public final void run() {
            doBeacon();
        }
    };

    private final IBeaconCallback mBeaconCallback = new IBeaconCallback() {
        @Override
        public final void onFinished(final BeaconResult _result) {
            if (!_result.getSuccess()) {
                startWithDelay(TimeUnit.SECONDS.toMillis(Constants.TIME_API_RECALL));
                return;
            }

            ArTvLogger.printMessage("Need update campaigns: " + (_result.needProcessCampaigns() ? "Yes" : "No"));
            ArTvLogger.printMessage("Messages assigned: " + (_result.needProcessMessages() ? "Yes" : "No"));

            if (_result.needProcessMessages()) processMsgBoardCampaign(_result.getMsgBoardCampaign());

            if (_result.needProcessCampaigns()) {
                processCampaigns(_result.getDeletedCampaignIds(), _result.getCampaigns());
            } else {
                startWithDelay(TimeUnit.MINUTES.toMillis(mGlobalConfig.getServerBeaconInterval()));
            }
        }
    };

    private final void processMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        mDbWorker.write(_msgBoardCampaign);
        mMessageWorker.stopMessages();
        mMessageWorker.playMessages();
    }

    private final void processCampaigns(final List<Integer> deletedCampaignIds, final List<Campaign> _campaigns) {
        if (!deletedCampaignIds.isEmpty()) {
            for (final Integer id : deletedCampaignIds) {
                mDbWorker.deleteCampaign(id);
            }
        }

        mCampaignWorker.loadCampaigns(_campaigns, mCampaignDownloadListener);
    }

    private final ICampaignDownloadListener mCampaignDownloadListener = new ICampaignDownloadListener() {
        @Override
        public final void onCampaignDownloadFinished(final ArTvResult _result) {
            ArTvLogger.printMessage("Campaigns update success: " + _result.getSuccess());
            if (_result.getSuccess()) {
                mPlaybackWorker.stopPlayback();
                mPlaybackWorker.startPlayback();
            }
            doBeacon();
        }

        @Override
        public final void onPercentLoaded(final double _percent) {
        }
    };

}
