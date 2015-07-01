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
}
