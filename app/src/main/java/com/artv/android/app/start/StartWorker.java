package com.artv.android.app.start;

import com.artv.android.ArTvResult;
import com.artv.android.core.beacon.BeaconResult;
import com.artv.android.core.beacon.BeaconWorker;
import com.artv.android.core.beacon.IBeaconCallback;
import com.artv.android.core.campaign.CampaignResult;
import com.artv.android.core.campaign.CampaignWorker;
import com.artv.android.core.campaign.ICampaignCallback;
import com.artv.android.core.campaign.ICampaignDownloadListener;
import com.artv.android.core.display.TurnOffWorker;
import com.artv.android.core.init.IInitCallback;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Beacon;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.database.DbWorker;
import com.artv.android.system.fragments.splash.ISplashFragmentListener;

import java.util.List;

/**
 * Created by ZOG on 8/10/2015.
 */
public class StartWorker {

    private InitWorker mInitWorker;
    private StateWorker mStateWorker;
    private CampaignWorker mCampaignWorker;
    private BeaconWorker mBeaconWorker;
    private DbWorker mDbWorker;
    private TurnOffWorker mTurnOffWorker;

    private ISplashFragmentListener mSplashFragmentListener;

    public final void setInitWorker(final InitWorker _worker) {
        mInitWorker = _worker;
    }

    public final void setStateWorker(final StateWorker _worker) {
        mStateWorker = _worker;
    }

    public final void setCampaignsWorker(final CampaignWorker _worker) {
        mCampaignWorker = _worker;
    }

    public final void setBeaconWorker(final BeaconWorker _beaconWorker) {
        mBeaconWorker = _beaconWorker;
    }

    public final void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final void setTurnOffWorker(final TurnOffWorker _worker) {
        mTurnOffWorker = _worker;
    }

    public final void setSplashFragmentListener(final ISplashFragmentListener _listener) {
        mSplashFragmentListener = _listener;
    }

    public final void beginInitializing() {
        ArTvLogger.printDivider();
        ArTvLogger.printMessage("Start initializing");

        mInitWorker.startInitializing(
                new IInitCallback() {
                    @Override
                    public final void onInitSuccess(final ArTvResult _result) {
                        ArTvLogger.printMessage("Initializing success");

                        mSplashFragmentListener.showProgressUi();
                        addDataToWorkers();
                        beginCampaignLogic();
                    }

                    @Override
                    public final void onInitFail(final ArTvResult _result) {
                        ArTvLogger.printMessage("Initializing failed, reason: " + _result.getMessage());
                    }
                }
        );
    }

    private final void addDataToWorkers() {
        mCampaignWorker.setInitData(mInitWorker.getInitData());
        mBeaconWorker.setInitData(mInitWorker.getInitData());
        mTurnOffWorker.setDeviceConfig(mInitWorker.getInitData().getDeviceConfig());
    }

    private final void beginCampaignLogic() {
        switch (mStateWorker.getArTvState()) {
            case STATE_APP_START:
                doInitialCampaignDownload();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                ArTvLogger.printDivider();
                ArTvLogger.printMessage("Check for new/updated campaigns and messages");
                doBeacon();
                break;
        }
    }

    private final void doInitialCampaignDownload() {
        ArTvLogger.printDivider();
        ArTvLogger.printMessage("Start initial campaign download");
        mCampaignWorker.doInitialCampaignDownload(new ICampaignDownloadListener() {
            @Override
            public final void onCampaignDownloadFinished(final ArTvResult _result) {
                if (_result.getSuccess()) {
                    ArTvLogger.printMessage("Initial campaign download success");
                    mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
                    mStateWorker.notifyStateChangeListeners();
                } else {
                    ArTvLogger.printMessage("Initial campaign download failed, reason: " + _result.getMessage());
                }
            }

            @Override
            public final void onPercentLoaded(final double _percent) {
                mSplashFragmentListener.onPercentLoaded(_percent);
            }
        });
    }

    private final void doBeacon() {
        mBeaconWorker.doBeacon(beaconCallback);
    }

    private final IBeaconCallback beaconCallback = new IBeaconCallback() {
        @Override
        public final void onFinished(final BeaconResult _result) {
            if (_result.getSuccess()) {
                processMsgBoardCampaign(_result.getMsgBoardCampaign());
                processCampaigns(_result.getDeletedCampaignIds(), _result.getCampaigns());
            } else {
                ArTvLogger.printMessage("Beacon failed, reason: " + _result.getMessage());
                mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
                mStateWorker.notifyStateChangeListeners();
            }
        }
    };

    private final void processMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        ArTvLogger.printMessage("Messages assigned: " + (_msgBoardCampaign == null ? "No" : "Yes"));
        mDbWorker.write(_msgBoardCampaign);
    }

    private final void processCampaigns(final List<Integer> deletedCampaignIds, final List<Campaign> _campaigns) {
        ArTvLogger.printMessage("Need update campaigns: " + ((deletedCampaignIds.isEmpty() || _campaigns.isEmpty()) ? "No" : "Yes"));
        if (!deletedCampaignIds.isEmpty()) {
            for (final Integer id : deletedCampaignIds) {
                mDbWorker.deleteCampaign(id);
            }
        }

        if (_campaigns.isEmpty()) {
            mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
            mStateWorker.notifyStateChangeListeners();
        } else {
            mCampaignWorker.loadCampaigns(_campaigns, updateLoadListener);
        }
    }

    private final ICampaignDownloadListener updateLoadListener = new ICampaignDownloadListener() {
        @Override
        public final void onCampaignDownloadFinished(final ArTvResult _result) {
            if (_result.getSuccess()) {
                ArTvLogger.printMessage("Campaigns updated successfully");
                mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
                mStateWorker.notifyStateChangeListeners();
            } else {
                ArTvLogger.printMessage("Error updating campaigns");
            }
        }

        @Override
        public final void onPercentLoaded(final double _percent) {
            mSplashFragmentListener.onPercentLoaded(_percent);
        }
    };

    public final void cancel() {
        ArTvLogger.printMessage("Cancel downloading campaign...");
        mCampaignWorker.cancelLoading();
    }

}
