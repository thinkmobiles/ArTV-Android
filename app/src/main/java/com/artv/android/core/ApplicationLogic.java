package com.artv.android.core;

import android.content.Context;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.beacon.BeaconWorker;
import com.artv.android.core.campaign.CampaignsWorker;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.display.DisplaySwitcher;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.database.DBManager;
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
    private CampaignsWorker mCampaignsWorker;
    private DbWorker mDbWorker;

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

        mBeaconWorker = new BeaconWorker();

        mInitWorker.setApiWorker(mApiWorker);
        mInitWorker.setDisplaySwitcher(mDisplaySwitcher);

        mDbWorker = DBManager.getInstance(mContext);

        mCampaignsWorker = new CampaignsWorker();
        mCampaignsWorker.setApiWorker(mApiWorker);
        mCampaignsWorker.setDbWorker(mDbWorker);
    }

    public final ConfigInfoWorker getConfigInfoWorker() {
        return mConfigInfoWorker;
    }

    public final StateWorker getStateWorker() {
        return mStateWorker;
    }

    public final ApiWorker getApiWorker() {
        return mApiWorker;
    }

    public final InitWorker getInitWorker() {
        return mInitWorker;
    }

    public final DisplaySwitcher getDisplaySwitcher() {
        return mDisplaySwitcher;
    }

    public final CampaignsWorker getCampaignWorker() {
        return mCampaignsWorker;
    }

    public final DbWorker getDbWorker() {
        return mDbWorker;
    }

    /**
     * Determine state when application start. Next steps will be triggered from UI.
     */
    public final void determineStateWhenAppStart() {
        mConfigInfoWorker.loadConfigInfo();

        if (!mConfigInfoWorker.getConfigInfo().hasConfigInfo()
                || !mCampaignsWorker.hasCampaignToPlay()) {
            mStateWorker.setState(ArTvState.STATE_APP_START);
        } else {
            mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
        }
    }

}
