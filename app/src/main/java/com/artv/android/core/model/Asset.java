package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
@Root(name = "Asset")
public final class Asset {

    @Element(name = "Name")
    private String mName;

    @Element(name = "URL")
    private String mURL;

    @Element(name = "Duration")
    private int mDuration;

    @Element(name = "Sequence")
    private int mSequence;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public int getmSequence() {
        return mSequence;
    }

    public void setmSequence(int mSequence) {
        this.mSequence = mSequence;
    }
}
