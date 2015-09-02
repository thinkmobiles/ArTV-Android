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
    public Integer duration;

    @Element(name = "Sequence", required = false)
    public Integer sequence;

    public final boolean isValid() {
        return name != null && !name.isEmpty() &&
                url != null && !url.isEmpty();
    }

    public final int getAssetId() {
        return Math.abs(hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Asset asset = (Asset) o;

        if (name != null ? !name.equals(asset.name) : asset.name != null) return false;
        if (url != null ? !url.equals(asset.url) : asset.url != null) return false;
        if (duration != null ? !duration.equals(asset.duration) : asset.duration != null)
            return false;
        return !(sequence != null ? !sequence.equals(asset.sequence) : asset.sequence != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (sequence != null ? sequence.hashCode() : 0);
        return result;
    }

    @Override
    public final String toString() {
        return "name: " + name + ", url: " + url;
    }
}
