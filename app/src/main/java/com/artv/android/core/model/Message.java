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
    public int sequence;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (sequence != message.sequence) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return !(position != null ? !position.equals(message.position) : message.position != null);

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + sequence;
        return result;
    }
}
