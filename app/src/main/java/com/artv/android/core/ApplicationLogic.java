package com.artv.android.core;

import android.content.Context;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.system.SpHelper;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ApplicationLogic {

    private Context mContext;

    private ArTvState mState = ArTvState.STATE_APP_START;
    private SpHelper mSpHelper;
    private ConfigInfoWorker mConfigInfoWorker;
    private ApiWorker mApiWorker;

    public ApplicationLogic(final Context _context) {
        mContext = _context;

        mApiWorker = new ApiWorker(mContext);
        mSpHelper = new SpHelper(mContext);
        mConfigInfoWorker = new ConfigInfoWorker();
        mConfigInfoWorker.setSpHelper(mSpHelper);

        determineStateWhenAppStart();
    }

    public final ArTvState getArTvState() {
        return mState;
    }

    public final ConfigInfoWorker getConfigInfoWorker() {
        return mConfigInfoWorker;
    }

    public final ApiWorker getApiWorker() {
        return mApiWorker;
    }

    private final void determineStateWhenAppStart() {
        mConfigInfoWorker.loadConfigInfo();

        if (!mConfigInfoWorker.getConfigInfo().hasConfigInfo()) {
            mState = ArTvState.STATE_APP_START;
        } else {
            mState = ArTvState.STATE_APP_START_WITH_CONFIG_INFO;
        }
    }

}
