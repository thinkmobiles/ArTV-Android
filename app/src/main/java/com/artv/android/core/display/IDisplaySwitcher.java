package com.artv.android.core.display;

/**
 * Created by ZOG on 7/8/2015.
 *
 * Used to turn on and off display.
 */
public interface IDisplaySwitcher {

    boolean isDisplayTurnedOn();
    void turnOn(final DisplaySwitcherAdapterCallback _callback);
    void turnOff(final DisplaySwitcherAdapterCallback _callback);

}
