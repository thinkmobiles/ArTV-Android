package com.artv.android.core.state;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZOG on 7/7/2015.
 */
public class StateWorker {

    private ArTvState mState = ArTvState.STATE_APP_START;

    private Set<IArTvStateChangeListener> mStateChangeListeners;

    public StateWorker() {
        mStateChangeListeners = new HashSet<>();
    }

    public final ArTvState getArTvState() {
        return mState;
    }

    public final boolean addStateChangeListener(final IArTvStateChangeListener _listener) {
        return mStateChangeListeners.add(_listener);
    }

    public final boolean removeStateChangeListener(final IArTvStateChangeListener _listener) {
        return mStateChangeListeners.remove(_listener);
    }

    public final void notifyStateChangeListeners() {
        for (final IArTvStateChangeListener listener : mStateChangeListeners) {
            listener.onArTvStateChanged();
        }
    }

    public final void setState(final ArTvState _state) {
        mState = _state;
    }

}
