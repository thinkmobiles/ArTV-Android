package com.artv.android.core.model;

import com.artv.android.app.message.MessagePosition;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
@Root(name = "Message")
public final class Message {

    private static final String KEY_RIGHT = "R";
    private static final String KEY_BOTTOM = "B";

    @Element(name = "Text")
    public String text;

    @Element(name = "Position")
    public String position;

    @Element(name = "Sequence")
    public int sequence;

    public final MessagePosition position() {
        if (KEY_RIGHT.equals(position)) return MessagePosition.RIGHT;
        else if (KEY_BOTTOM.equals(position)) return MessagePosition.BOTTOM;
        else return MessagePosition.UNDEFINED;
    }

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
