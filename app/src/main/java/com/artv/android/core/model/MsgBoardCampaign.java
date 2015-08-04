package com.artv.android.core.model;

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
    public int msgBoardID;

    @Element(name = "CRCVersion", required = false)
    public int crcVersion;

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
                "mMsgBoardID=" + mMsgBoardID +
                ", mCRCVersion=" + mCRCVersion +
                ", mStartDate='" + mStartDate + '\'' +
                ", mEndDate='" + mEndDate + '\'' +
                ", mPlayDay='" + mPlayDay + '\'' +
                ", mTextColor='" + mTextColor + '\'' +
                ", mRightBkgURL='" + mRightBkgURL + '\'' +
                ", mBottomBkgURL='" + mBottomBkgURL + '\'' +
                ", mMessages=" + mMessages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        MsgBoardCampaign msgBoardCampaign = (MsgBoardCampaign) o;
        if(mMsgBoardID != msgBoardCampaign.mMsgBoardID) return false;
        if(mCRCVersion != msgBoardCampaign.mCRCVersion) return false;
        if(!mEndDate.equals(msgBoardCampaign.mEndDate)) return false;
        if(!mStartDate.equals(msgBoardCampaign.mStartDate)) return false;
        if(!mTextColor.equals(msgBoardCampaign.mTextColor)) return false;
        if(!mPlayDay.equals(msgBoardCampaign.mPlayDay)) return false;
        if(!mBottomBkgURL.equals(msgBoardCampaign.mBottomBkgURL)) return false;
        if(!mRightBkgURL.equals(msgBoardCampaign.mRightBkgURL)) return false;

        for (int i=0; i < mMessages.size(); i++) {
            if(!mMessages.get(i).equals(msgBoardCampaign.mMessages.get(i))) return false;
        }

        return true;
    }

}
