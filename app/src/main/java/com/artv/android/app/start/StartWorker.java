package com.artv.android.app.start;

import com.artv.android.ArTvResult;
import com.artv.android.core.beacon.BeaconWorker;
import com.artv.android.core.campaign.CampaignResult;
import com.artv.android.core.campaign.CampaignWorker;
import com.artv.android.core.campaign.ICampaignCallback;
import com.artv.android.core.campaign.ICampaignDownloadListener;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.init.IInitCallback;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.database.DbWorker;
import com.artv.android.system.fragments.splash.ISplashFragmentListener;

/**
 * Created by ZOG on 8/10/2015.
 */
public class StartWorker {

    private InitWorker mInitWorker;
    private ConfigInfoWorker mConfigInfoWorker;
    private StateWorker mStateWorker;
    private CampaignWorker mCampaignWorker;
    private BeaconWorker mBeaconWorker;
    private DbWorker mDbWorker;

    private ISplashFragmentListener mSplashFragmentListener;

    public final void setInitWorker(final InitWorker _worker) {
        mInitWorker = _worker;
    }

    public final void setConfigInfoWorker(final ConfigInfoWorker _worker) {
        mConfigInfoWorker = _worker;
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

    public final void setSplashFragmentListener(final ISplashFragmentListener _listener) {
        mSplashFragmentListener = _listener;
    }

    public final void beginInitializing() {
        mInitWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());
        ArTvLogger.printMessage("Start initializing");

        mInitWorker.startInitializing(
                new IInitCallback() {
                    @Override
                    public final void onInitSuccess(final ArTvResult _result) {
                        ArTvLogger.printMessage("Initializing success");
                        mSplashFragmentListener.showProgressUi();
                        beginCampaignLogic();
                    }

                    @Override
                    public final void onInitFail(final ArTvResult _result) {
                        ArTvLogger.printMessage("Initializing failed, reason: " + _result.getMessage());
                    }
                }
        );
    }

    private final void beginCampaignLogic() {
        mCampaignWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());
        mCampaignWorker.setInitData(mInitWorker.getInitData());

        switch (mStateWorker.getArTvState()) {
            case STATE_APP_START:
                doInitialCampaignDownload();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                ArTvLogger.printMessage("Has campaigns to play");
//                doBeaconRequest();
                mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
                break;
        }
    }

    private final void doInitialCampaignDownload() {
        ArTvLogger.printMessage("Start initial campaign download");
        mCampaignWorker.doInitialCampaignDownload(new ICampaignDownloadListener() {
            @Override
            public final void onCampaignDownloadFinished(final ArTvResult _result) {
                if (_result.getSuccess()) {
                    ArTvLogger.printMessage("Initial campaign download success");
                    mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
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

    public final void cancel() {
        ArTvLogger.printMessage("Cancel downloading campaign...");
        mCampaignWorker.cancelLoading();
    }

    private final void doBeaconRequest() {
//        mBeaconWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());
//        mBeaconWorker.setInitData(mInitWorker.getInitData());
//        mBeaconWorker.doBeacon(beaconCallback);
    }

}
