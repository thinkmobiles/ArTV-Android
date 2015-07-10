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
    private long mMsgBoardID;

    @Element(name = "CRCVersion", required = false)
    private int mCRCVersion;

    @Element(name = "StartDate", required = false)
    private String mStartDate;

    @Element(name = "EndDate", required = false)
    private String mEndDate;

    @Element(name = "PlayDay", required = false)
    private String mPlayDay;

    @Element(name = "TextColor", required = false)
    private String mTextColor;

    @Element(name = "RightBkgURL", required = false)
    private String mRightBkgURL;

    @Element(name = "BottomBkgURL", required = false)
    private String mBottomBkgURL;

    @ElementList(name = "Messages", required = false)
    private List<Message> mMessages;

}
