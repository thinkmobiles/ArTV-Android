package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by
 * Rogach on 01.07.2015.
 */
@Root(name = "Entry")
public final class Entry {

    @Element(name = "Name")
    public String name;

    @Element(name = "Value")
    public String value;

}
