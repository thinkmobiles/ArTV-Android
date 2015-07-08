package com.artv.android.core;

import android.content.Context;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.display.DisplaySwitcher;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.system.SpHelper;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ApplicationLogic {

    private Context mContext;

    private SpHelper mSpHelper;
    private StateWorker mStateWorker;
    private ConfigInfoWorker mConfigInfoWorker;
    private ApiWorker mApiWorker;
    private InitWorker mInitWorker;
    private DisplaySwitcher mDisplaySwitcher;

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

        mInitWorker.setApiWorker(mApiWorker);
        mInitWorker.setDisplaySwitcher(mDisplaySwitcher);

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

    public final InitWorker getInitWorker() {
        return mInitWorker;
    }

    public final DisplaySwitcher getDisplaySwitcher() {
        return mDisplaySwitcher;
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

}
