package com.artv.android.app.start;

import com.artv.android.ArTvResult;
import com.artv.android.core.beacon.BeaconWorker;
import com.artv.android.core.campaign.CampaignsWorker;
import com.artv.android.core.campaign.ICampaignDownloadListener;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.init.IInitCallback;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.system.fragments.splash.ISplashFragmentListener;

/**
 * Created by ZOG on 8/10/2015.
 */
public final class StartWorker {

    private InitWorker mInitWorker;
    private ConfigInfoWorker mConfigInfoWorker;
    private StateWorker mStateWorker;
    private CampaignsWorker mCampaignsWorker;
    private BeaconWorker mBeaconWorker;

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

    public final void setCampaignsWorker(final CampaignsWorker _worker) {
        mCampaignsWorker = _worker;
    }

    public final void setBeaconWorker(final BeaconWorker _beaconWorker) {
        mBeaconWorker = _beaconWorker;
    }

    public final void setSplashFragmentListener(final ISplashFragmentListener _listener) {
        mSplashFragmentListener = _listener;
    }

    public final void beginInitializing() {
        mInitWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());

        mInitWorker.startInitializing(
                new IInitCallback() {
                    @Override
                    public final void onInitSuccess(final ArTvResult _result) {
                        ArTvLogger.printMessage(_result.getMessage());
                        mSplashFragmentListener.showProgressUi();
                        beginCampaignLogic();
                    }

                    @Override
                    public final void onProgress(final ArTvResult _result) {
                        ArTvLogger.printMessage(_result.getMessage());
                    }

                    @Override
                    public final void onInitFail(final ArTvResult _result) {
                        ArTvLogger.printMessage(_result.getMessage());
                    }
                }
        );
    }

    private final void beginCampaignLogic() {
        mCampaignsWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());
        mCampaignsWorker.setInitData(mInitWorker.getInitData());

        switch (mStateWorker.getArTvState()) {
            case STATE_APP_START:
                doInitialCampaignDownload();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                ArTvLogger.printMessage("Has campaigns to play");
                mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
                doBeaconRequest();
                break;
        }
    }

    private final void doInitialCampaignDownload() {
        mCampaignsWorker.doInitialCampaignDownload(new ICampaignDownloadListener() {
            @Override
            public final void onCampaignDownloadFinished(final ArTvResult _result) {
                if (_result.getSuccess()) {
                    mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
                } else {
                    ArTvLogger.printMessage("Initial loading failed, reason: " + _result.getMessage());
                }
            }

            @Override
            public final void onPercentLoaded(final double _percent) {
                mSplashFragmentListener.onPercentLoaded(_percent);
            }
        });
    }

    public final void cancel() {
        mCampaignsWorker.cancelLoading();
    }

    private final void doBeaconRequest() {
        mBeaconWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());
        mBeaconWorker.setInitData(mInitWorker.getInitData());
        mBeaconWorker.doBeacon();
    }

}
