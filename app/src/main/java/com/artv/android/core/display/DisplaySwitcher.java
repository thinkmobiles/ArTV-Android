package com.artv.android.core.display;

/**
 * Created by ZOG on 7/8/2015.
 */
public final class DisplaySwitcher implements IDisplaySwitcher {

    @Override
    public final boolean isDisplayTurnedOn() {
        return false;
    }

    @Override
    public final void turnOn(final DisplaySwitcherAdapterCallback _callback) {
        _callback.turnedOn();
    }

    @Override
    public final void turnOff(final DisplaySwitcherAdapterCallback _callback) {
        _callback.turnedOff();
    }

}
