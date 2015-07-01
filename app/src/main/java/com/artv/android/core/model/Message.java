package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
@Root(name = "Message")
public final class Message {

    @Element(name = "Text")
    private String mText;

    @Element(name = "Position")
    private String mPosition;

    @Element(name = "Sequence")
    private int mSequenceL;

}
