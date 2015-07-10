package com.artv.android.core.display;

/**
 * Created by ZOG on 7/8/2015.
 *
 * Listener can simply implement only needed method.
 */
public abstract class DisplaySwitcherAdapterCallback {

    public void turnedOn() {}
    public void turnedOff() {}
    public abstract void switchFailed();    //todo: check is this needed

}
