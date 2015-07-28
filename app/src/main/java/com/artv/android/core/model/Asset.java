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
}
