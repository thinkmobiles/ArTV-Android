package com.artv.android;

import android.content.Context;

import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.app.start.StartWorker;
import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.beacon.BeaconWorker;
import com.artv.android.core.campaign.CampaignWorker;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.date.DateWorker;
import com.artv.android.core.display.DisplaySwitcher;
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
    private DisplaySwitcher mDisplaySwitcher;
    private BeaconWorker mBeaconWorker;
    private CampaignWorker mCampaignWorker;
    private DbWorker mDbWorker;
    private DateWorker mDateWorker;

    private StartWorker mStartWorker;
    private PlaybackWorker mPlaybackWorker;

    public ApplicationLogic(final Context _context) {
        mContext = _context;

        mSpHelper = new SpHelper(mContext);

        mStateWorker = new StateWorker();

        mConfigInfoWorker = new ConfigInfoWorker();
        mConfigInfoWorker.setSpHelper(mSpHelper);
        mConfigInfoWorker.setStateWorker(mStateWorker);

        mApiWorker = new ApiWorker(mContext);

        mInitWorker = new InitWorker();
        mDisplaySwitcher = new DisplaySwitcher();

        mDbWorker = DbManager.getInstance(mContext);

        mDateWorker = new DateWorker();

        mBeaconWorker = new BeaconWorker();
        mBeaconWorker.setApiWorker(mApiWorker);
        mBeaconWorker.setDateWorker(mDateWorker);
        mBeaconWorker.setDbWorker(mDbWorker);

        mInitWorker.setApiWorker(mApiWorker);
        mInitWorker.setDisplaySwitcher(mDisplaySwitcher);

        mCampaignWorker = new CampaignWorker();
        mCampaignWorker.setApiWorker(mApiWorker);
        mCampaignWorker.setDbWorker(mDbWorker);

        mStartWorker = new StartWorker();
        mStartWorker.setInitWorker(mInitWorker);
        mStartWorker.setConfigInfoWorker(mConfigInfoWorker);
        mStartWorker.setStateWorker(mStateWorker);
        mStartWorker.setCampaignsWorker(mCampaignWorker);
        mStartWorker.setBeaconWorker(mBeaconWorker);
        mStartWorker.setDbWorker(mDbWorker);

        mPlaybackWorker = new PlaybackWorker();
        mPlaybackWorker.setDbWorker(mDbWorker);
    }

    public final ConfigInfoWorker getConfigInfoWorker() {
        return mConfigInfoWorker;
    }

    public final StateWorker getStateWorker() {
        return mStateWorker;
    }

    public final DisplaySwitcher getDisplaySwitcher() {
        return mDisplaySwitcher;
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

    /**
     * Determine state when application start. Next steps will be triggered from UI.
     */
    public final void determineStateWhenAppStart() {
        mConfigInfoWorker.loadConfigInfo();

        if (!mConfigInfoWorker.getConfigInfo().hasConfigInfo() ||
                !mCampaignWorker.hasCampaignToPlay()) {
            mStateWorker.setState(ArTvState.STATE_APP_START);
        } else {
            mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
        }
    }

}
