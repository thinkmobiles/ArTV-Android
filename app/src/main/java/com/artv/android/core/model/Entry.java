package com.artv.android.core.model;

import org.simpleframework.xml.Element;

/**
 * Created by
 * Rogach on 01.07.2015.
 */
public final class Entry {

    @Element(name = "Name")
    private String mName;

    @Element(name = "Value")
    private String mValue;

}
