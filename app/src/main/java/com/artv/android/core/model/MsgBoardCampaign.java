package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        MsgBoardCampaign msgBoardCampaign = (MsgBoardCampaign) o;
        if(msgBoardId != msgBoardCampaign.msgBoardId) return false;
        if(crcVersion != msgBoardCampaign.crcVersion) return false;
        if(!endDate.equals(msgBoardCampaign.endDate)) return false;
        if(!startDate.equals(msgBoardCampaign.startDate)) return false;
        if(!textColor.equals(msgBoardCampaign.textColor)) return false;
        if(!playDay.equals(msgBoardCampaign.playDay)) return false;
        if(!bottomBkgURL.equals(msgBoardCampaign.bottomBkgURL)) return false;
        if(!rightBkgURL.equals(msgBoardCampaign.rightBkgURL)) return false;

        for (int i=0; i < messages.size(); i++) {
            if(!messages.get(i).equals(msgBoardCampaign.messages.get(i))) return false;
        }

        return true;
    }

}
