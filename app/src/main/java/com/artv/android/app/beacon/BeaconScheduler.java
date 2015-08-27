package com.artv.android.app.beacon;

import android.os.Handler;

import com.artv.android.ArTvResult;
import com.artv.android.app.message.MessageWorker;
import com.artv.android.core.beacon.BeaconWorker;
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

/**
 * Created by ZOG on 8/27/2015.
 */
public final class BeaconScheduler {

    private BeaconWorker mBeaconWorker;
    private GlobalConfig mGlobalConfig;
    private DbWorker mDbWorker;
    private MessageWorker mMessageWorker;
    private CampaignWorker mCampaignWorker;

    private Handler mHandler;

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

    public final void start() {
        createHandler();

        mBeaconWorker.doBeacon(mBeaconCallback);
    }

    public final void stop() {
        mCampaignWorker.cancelLoading();
        mHandler.removeCallbacks(delay);
    }

    private final void createHandler() {
        if (mHandler == null) mHandler = new Handler();
    }

    private final void startWithDelay(final long _delay) {
        mHandler.postDelayed(delay, _delay);
    }

    private final Runnable delay = new Runnable() {
        @Override
        public final void run() {
            start();
        }
    };

    private final ICampaignCallback mBeaconCallback = new ICampaignCallback() {
        @Override
        public final void onFinished(final CampaignResult _result) {
            if (!_result.getSuccess()) {
                ArTvLogger.printMessage("Beacon failed, reason: " + _result.getMessage());
                startWithDelay(10 * 1000);
                return;
            }

            ArTvLogger.printMessage("Campaigns to update: " + _result.getCampaigns().size());
            ArTvLogger.printMessage("Has MsgBoardMessage " + (_result.getMsgBoardCampaign() != null));

            processMsgBoardCampaign(_result.getMsgBoardCampaign());
            processCampaigns(_result.getCampaigns());

            startWithDelay(mGlobalConfig.getServerBeaconInterval());
        }
    };

    private final void processMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        mDbWorker.write(_msgBoardCampaign);
        mMessageWorker.stopMessages();
        mMessageWorker.playMessages();
    }

    private final void processCampaigns(final List<Campaign> _campaigns) {
        if (!_campaigns.isEmpty()) {
            mCampaignWorker.loadCampaigns(_campaigns, mCampaignDownloadListener);
        }
    }

    private final ICampaignDownloadListener mCampaignDownloadListener = new ICampaignDownloadListener() {
        @Override
        public final void onCampaignDownloadFinished(final ArTvResult _result) {
            ArTvLogger.printMessage("Campaigns update success: " + _result.getSuccess());
            //restart campaigns play
        }

        @Override
        public final void onPercentLoaded(final double _percent) {
            if (_percent % 10 < 1)  ArTvLogger.printMessage(String.format("Loaded %.2f%%", _percent));
        }
    };

}
