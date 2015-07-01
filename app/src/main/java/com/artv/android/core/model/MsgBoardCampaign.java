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
    private long mMsgBoardID;

    @Element(name = "CRCVersion")
    private int mCRCVersion;

    @Element(name = "StartDate")
    private String mStartDate;

    @Element(name = "EndDate")
    private String mEndDate;

    @Element(name = "PlayDay")
    private String mPlayDay;

    @Element(name = "TextColor")
    private String mTextColor;

    @Element(name = "RightBkgURL")
    private String mRightBkgURL;

    @Element(name = "BottomBkgURL")
    private String mBottomBkgURL;

    @ElementList(name = "Messages")
    private List<Message> mMessages;

}
