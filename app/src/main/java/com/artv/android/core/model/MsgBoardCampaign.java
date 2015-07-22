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

    public long getmMsgBoardID() {
        return mMsgBoardID;
    }

    public void setmMsgBoardID(long mMsgBoardID) {
        this.mMsgBoardID = mMsgBoardID;
    }

    public int getmCRCVersion() {
        return mCRCVersion;
    }

    public void setmCRCVersion(int mCRCVersion) {
        this.mCRCVersion = mCRCVersion;
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public String getmPlayDay() {
        return mPlayDay;
    }

    public void setmPlayDay(String mPlayDay) {
        this.mPlayDay = mPlayDay;
    }

    public String getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(String mTextColor) {
        this.mTextColor = mTextColor;
    }

    public String getmRightBkgURL() {
        return mRightBkgURL;
    }

    public void setmRightBkgURL(String mRightBkgURL) {
        this.mRightBkgURL = mRightBkgURL;
    }

    public String getmBottomBkgURL() {
        return mBottomBkgURL;
    }

    public void setmBottomBkgURL(String mBottomBkgURL) {
        this.mBottomBkgURL = mBottomBkgURL;
    }

    public List<Message> getmMessages() {
        return mMessages;
    }

    public void setmMessages(List<Message> mMessages) {
        this.mMessages = mMessages;
    }
}
