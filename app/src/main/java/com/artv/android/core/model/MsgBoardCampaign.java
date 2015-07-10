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

    @Element(name = "MsgBoardID")
    public long mMsgBoardID;

    @Element(name = "CRCVersion")
    public int mCRCVersion;

    @Element(name = "StartDate")
    public String mStartDate;

    @Element(name = "EndDate")
    public String mEndDate;

    @Element(name = "PlayDay")
    public String mPlayDay;

    @Element(name = "TextColor")
    public String mTextColor;

    @Element(name = "RightBkgURL")
    public String mRightBkgURL;

    @Element(name = "BottomBkgURL")
    public String mBottomBkgURL;

    @ElementList(name = "Messages")
    public List<Message> mMessages;

}
