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
    private String mName;
    @Element(name = "Value")
    private String mValue;

    public final String getName() {
        return mName;
    }

    public final void setName(final String _name) {
        mName = _name;
    }

    public final String getValue() {
        return mValue;
    }

    public final void setValue(final String _value) {
        mValue = _value;
    }

}
