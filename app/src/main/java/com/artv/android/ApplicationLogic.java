package com.artv.android;

import android.content.Context;

import com.artv.android.app.beacon.BeaconScheduler;
import com.artv.android.app.message.MessageWorker;
import com.artv.android.app.playback.PlayModeManager;
import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.app.start.StartWorker;
import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.beacon.BeaconWorker;
import com.artv.android.core.campaign.CampaignWorker;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.date.DateWorker;
import com.artv.android.core.display.DeviceAdministrator;
import com.artv.android.core.display.TurnOffWorker;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.database.DbManager;
import com.artv.android.database.DbWorker;
import com.artv.android.system.SpHelper;

/**
 * Created by ZOG on 6/30/2015.
 * todo: in first run no need call beacon, just download all campaigns
 */
public final class ApplicationLogic {

    private Context mContext;

    private SpHelper mSpHelper;
    private StateWorker mStateWorker;
    private ConfigInfoWorker mConfigInfoWorker;
    private ApiWorker mApiWorker;
    private InitWorker mInitWorker;
    private BeaconWorker mBeaconWorker;
    private CampaignWorker mCampaignWorker;
    private DbWorker mDbWorker;
    private DateWorker mDateWorker;
    private PlayModeManager mPlayModeManager;
    private DeviceAdministrator mDeviceAdministrator;

    private TurnOffWorker mTurnOffWorker;
    private StartWorker mStartWorker;
    private PlaybackWorker mPlaybackWorker;
    private MessageWorker mMessageWorker;
    private BeaconScheduler mBeaconScheduler;

    public ApplicationLogic(final Context _context) {
        mContext = _context;

        mSpHelper = new SpHelper(mContext);

        mStateWorker = new StateWorker();

        mApiWorker = new ApiWorker(mContext);

        mDbWorker = DbManager.getInstance(mContext);
        mPlayModeManager = new PlayModeManager();
        mDeviceAdministrator = new DeviceAdministrator(mContext);
        mTurnOffWorker = new TurnOffWorker(mContext, mPlayModeManager);
        mDeviceAdministrator.setTurnOffWorker(mTurnOffWorker);

        mPlaybackWorker = new PlaybackWorker();
        mPlaybackWorker.setContext(mContext);
        mPlaybackWorker.setDbWorker(mDbWorker);
        mPlaybackWorker.setPlayModeManager(mPlayModeManager);
        mPlaybackWorker.setTurnOffWorker(mTurnOffWorker);

        mMessageWorker = new MessageWorker();
        mMessageWorker.setDbWorker(mDbWorker);

        mDateWorker = new DateWorker();

        mCampaignWorker = new CampaignWorker();
        mCampaignWorker.setApiWorker(mApiWorker);
        mCampaignWorker.setDbWorker(mDbWorker);

        mBeaconWorker = new BeaconWorker();
        mBeaconWorker.setApiWorker(mApiWorker);
        mBeaconWorker.setDateWorker(mDateWorker);
        mBeaconWorker.setDbWorker(mDbWorker);
        mBeaconWorker.setPlaybackWorker(mPlaybackWorker);

        mBeaconScheduler = new BeaconScheduler();
        mBeaconScheduler.setBeaconWorker(mBeaconWorker);
        mBeaconScheduler.setDbWorker(mDbWorker);
        mBeaconScheduler.setMessageWorker(mMessageWorker);
        mBeaconScheduler.setCampaignsWorker(mCampaignWorker);
        mBeaconScheduler.setPlaybackWorker(mPlaybackWorker);

        mInitWorker = new InitWorker();
        mInitWorker.setPlaybackWorker(mPlaybackWorker);
        mInitWorker.setMessageWorker(mMessageWorker);
        mInitWorker.setBeaconScheduler(mBeaconScheduler);

        mConfigInfoWorker = new ConfigInfoWorker();
        mConfigInfoWorker.setSpHelper(mSpHelper);
        mConfigInfoWorker.setStateWorker(mStateWorker);
        mConfigInfoWorker.addConfigInfoListener(mBeaconWorker);
        mConfigInfoWorker.addConfigInfoListener(mCampaignWorker);
        mConfigInfoWorker.addConfigInfoListener(mInitWorker);

        mInitWorker.setApiWorker(mApiWorker);

        mStartWorker = new StartWorker();
        mStartWorker.setInitWorker(mInitWorker);
        mStartWorker.setStateWorker(mStateWorker);
        mStartWorker.setCampaignsWorker(mCampaignWorker);
        mStartWorker.setBeaconWorker(mBeaconWorker);
        mStartWorker.setDbWorker(mDbWorker);
        mStartWorker.setTurnOffWorker(mTurnOffWorker);
        mDeviceAdministrator.setStateWorker(mStateWorker);
    }

    public final ConfigInfoWorker getConfigInfoWorker() {
        return mConfigInfoWorker;
    }

    public final StateWorker getStateWorker() {
        return mStateWorker;
    }

    public final DbWorker getDbWorker() {
        return mDbWorker;
    }

    public final StartWorker getStartWorker() {
        return mStartWorker;
    }

    public final InitWorker getInitWorker() {
        return mInitWorker;
    }

    public final PlaybackWorker getPlaybackWorker() {
        return mPlaybackWorker;
    }

    public final MessageWorker getMessageWorker() {
        return mMessageWorker;
    }

    public PlayModeManager getPlayModeManager() {
        return mPlayModeManager;
    }

    public TurnOffWorker getTurnOffWorker() {
        return mTurnOffWorker;
    }

    public final BeaconScheduler getBeaconScheduler() {
        return mBeaconScheduler;
    }

    public DeviceAdministrator getDeviceAdministrator() {
        return mDeviceAdministrator;
    }

    /**
     * Determine state when application start. Next steps will be triggered from UI.
     */
    public final void determineStateWhenAppStart() {
        mConfigInfoWorker.loadConfigInfo();
        mConfigInfoWorker.notifyConfigInfoLoaded();

        if (!mConfigInfoWorker.getConfigInfo().hasConfigInfo() ||
                !mCampaignWorker.hasCampaignToPlay()) {
            mStateWorker.setState(ArTvState.STATE_APP_START);
        } else {
            mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
        }
        mStateWorker.notifyStateChangeListeners();
    }

}
