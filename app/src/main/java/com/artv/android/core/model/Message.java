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

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmPosition() {
        return mPosition;
    }

    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public int getmSequenceL() {
        return mSequenceL;
    }

    public void setmSequenceL(int mSequenceL) {
        this.mSequenceL = mSequenceL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if(!mText.equals(message.mText)) return false;
        if(!mPosition.equals(message.mPosition)) return false;
        if(mSequenceL != message.mSequenceL) return false;

        return true;
    }
}
