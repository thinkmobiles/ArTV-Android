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
    public long mMsgBoardID;

    @Element(name = "CRCVersion", required = false)
    public int mCRCVersion;

    @Element(name = "StartDate", required = false)
    public String mStartDate;

    @Element(name = "EndDate", required = false)
    public String mEndDate;

    @Element(name = "PlayDay", required = false)
    public String mPlayDay;

    @Element(name = "TextColor", required = false)
    public String mTextColor;

    @Element(name = "RightBkgURL", required = false)
    public String mRightBkgURL;

    @Element(name = "BottomBkgURL", required = false)
    public String mBottomBkgURL;

    @ElementList(name = "Messages", required = false)
    public List<Message> mMessages;

}
