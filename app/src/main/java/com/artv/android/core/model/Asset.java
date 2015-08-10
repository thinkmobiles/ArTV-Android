package com.artv.android.core.model;

import android.text.TextUtils;

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

        if(!TextUtils.equals(name,asset.name)) return false;
        if(!TextUtils.equals(url,asset.url)) return false;
        if(duration != asset.duration) return false;
        if(sequence != asset.sequence) return false;

        return true;
    }
}
