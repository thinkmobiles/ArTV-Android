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
    public String text;

    @Element(name = "Position")
    public String position;

    @Element(name = "Sequence")
    public int sequenceL;

}
