package com.artv.android.core;

import android.content.Context;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.config_info.IConfigInfoListener;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.IArTvStateChangeListener;
import com.artv.android.core.state.StateWorker;
import com.artv.android.system.SpHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ApplicationLogic implements IConfigInfoListener {

    private Context mContext;

    private SpHelper mSpHelper;
    private ConfigInfoWorker mConfigInfoWorker;
    private StateWorker mStateWorker;
    private ApiWorker mApiWorker;

    public ApplicationLogic(final Context _context) {
        mContext = _context;

        mSpHelper = new SpHelper(mContext);
        mConfigInfoWorker = new ConfigInfoWorker();
        mConfigInfoWorker.setSpHelper(mSpHelper);
        mStateWorker = new StateWorker();
        mApiWorker = new ApiWorker(mContext);

        determineStateWhenAppStart();
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

    public final IConfigInfoListener getConfigInfoListener() {
        return this;
    }

    /**
     * Determine state when application start. Next steps will be triggered from UI.
     */
    private final void determineStateWhenAppStart() {
        mConfigInfoWorker.loadConfigInfo();

        if (!mConfigInfoWorker.getConfigInfo().hasConfigInfo()) {
            mStateWorker.setState(ArTvState.STATE_APP_START);
        } else {
            mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
        }
    }

    @Override
    public final void onEnteredConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfoWorker.setConfigInfo(_configInfo);
        mConfigInfoWorker.saveConfigInfo();

        mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
    }

    @Override
    public final void onNeedRemoveConfigInfo() {
        mConfigInfoWorker.removeConfigInfo();

        mStateWorker.setState(ArTvState.STATE_APP_START);
    }
}
