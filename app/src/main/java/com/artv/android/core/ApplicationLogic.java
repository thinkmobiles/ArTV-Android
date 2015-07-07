package com.artv.android.core;

import android.content.Context;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.config_info.IConfigInfoListener;
import com.artv.android.system.SpHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ApplicationLogic implements IConfigInfoListener {

    private Context mContext;

    private ArTvState mState = ArTvState.STATE_APP_START;
    private SpHelper mSpHelper;
    private ConfigInfoWorker mConfigInfoWorker;
    private ApiWorker mApiWorker;

    private Set<IArTvStateChangeListener> mStateChangeListeners;

    public ApplicationLogic(final Context _context) {
        mContext = _context;

        mApiWorker = new ApiWorker(mContext);
        mSpHelper = new SpHelper(mContext);
        mConfigInfoWorker = new ConfigInfoWorker();
        mConfigInfoWorker.setSpHelper(mSpHelper);

        mStateChangeListeners = new HashSet<>();

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

    public final boolean addStateChangeListener(final IArTvStateChangeListener _listener) {
        return mStateChangeListeners.add(_listener);
    }

    public final boolean removeStateChangeListener(final IArTvStateChangeListener _listener) {
        return mStateChangeListeners.remove(_listener);
    }

    private final void notifyStateChangeListeners() {
        for (final IArTvStateChangeListener listener : mStateChangeListeners) {
            listener.onArTvStateChanged();
        }
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
            mState = ArTvState.STATE_APP_START;
        } else {
            mState = ArTvState.STATE_APP_START_WITH_CONFIG_INFO;
        }
    }

    @Override
    public final void onEnteredConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfoWorker.setConfigInfo(_configInfo);
        mConfigInfoWorker.saveConfigInfo();

        mState = ArTvState.STATE_APP_START_WITH_CONFIG_INFO;
        notifyStateChangeListeners();
    }

    @Override
    public final void onNeedClearConfigInfo() {
        mConfigInfoWorker.clearConfigInfo();

        mState = ArTvState.STATE_APP_START;
        notifyStateChangeListeners();
    }
}
