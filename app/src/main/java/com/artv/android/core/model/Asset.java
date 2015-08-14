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

    public final int getAssetId() {
        return hashCode();
    }

    @Override
    public final boolean equals(final Object _o) {
        if (this == _o) return true;
        if (_o == null) return false;
        if (getClass() != _o.getClass()) return false;

        final Asset asset = (Asset) _o;

        if(!TextUtils.equals(name,asset.name)) return false;
        if(!TextUtils.equals(url,asset.url)) return false;
        if(duration != asset.duration) return false;
        if(sequence != asset.sequence) return false;

        return true;
    }

    @Override
    public final int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + sequence;
        return result;
    }
}
