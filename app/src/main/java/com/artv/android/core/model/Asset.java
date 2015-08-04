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
    public String name;

    @Element(name = "URL", required = false)
    public String url;

    @Element(name = "Duration", required = false)
    public int duration;

    @Element(name = "Sequence", required = false)
    public int sequence;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Asset asset = (Asset) o;

        if(!mName.equals(asset.mName)) return false;
        if(!mURL.equals(asset.mURL)) return false;
        if(mDuration != asset.mDuration) return false;
        if(mSequence != asset.mSequence) return false;

        return true;
    }
}
