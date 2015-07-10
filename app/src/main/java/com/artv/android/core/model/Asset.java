package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
@Root(name = "Asset")
public final class Asset {

    @Element(name = "Name", required = false)
    private String mName;

    @Element(name = "URL", required = false)
    private String mURL;

    @Element(name = "Duration", required = false)
    private int mDuration;

    @Element(name = "Sequence", required = false)
    private int mSequence;
}
