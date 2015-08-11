package com.artv.android.core.model;

import android.text.TextUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Rogach on 30.06.2015.
 */
@Root(name = "MsgBoardCampaign")
public final class MsgBoardCampaign {

    @Element(name = "MsgBoardID", required = false)
    public int msgBoardId;

    @Element(name = "CRCVersion", required = false)
    public String crcVersion;

    @Element(name = "StartDate", required = false)
    public String startDate;

    @Element(name = "EndDate", required = false)
    public String endDate;

    @Element(name = "PlayDay", required = false)
    public String playDay;

    @Element(name = "TextColor", required = false)
    public String textColor;

    @Element(name = "RightBkgURL", required = false)
    public String rightBkgURL;

    @Element(name = "BottomBkgURL", required = false)
    public String bottomBkgURL;

    @ElementList(name = "Messages", required = false)
    public List<Message> messages;

    @Override
    public String toString() {
        return "MsgBoardCampaign{" +
                "mMsgBoardID=" + msgBoardId +
                ", mCRCVersion=" + crcVersion +
                ", mStartDate='" + startDate + '\'' +
                ", mEndDate='" + endDate + '\'' +
                ", mPlayDay='" + playDay + '\'' +
                ", mTextColor='" + textColor + '\'' +
                ", mRightBkgURL='" + rightBkgURL + '\'' +
                ", mBottomBkgURL='" + bottomBkgURL + '\'' +
                ", mMessages=" + messages +
                '}';
    }

    @Override
    public final boolean equals(final Object _o) {
        if (this == _o) return true;
        if (_o == null) return false;
        if (getClass() != _o.getClass()) return false;

        final MsgBoardCampaign msg = (MsgBoardCampaign) _o;
        return msgBoardId == msg.msgBoardId && String.valueOf(crcVersion).equals(String.valueOf(msg.crcVersion));
    }

}
