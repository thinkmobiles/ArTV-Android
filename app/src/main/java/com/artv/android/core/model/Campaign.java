package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
@Root(name = "Campaign")
public final class Campaign {

    @Element(name = "CampaignID")
    private long mCampaignID;

    @Element(name = "CRCVersion")
    private int mCRCVersion;

    @Element(name = "StartDate")
    private String mStartDate;

    @Element(name = "EndDate")
    private String mEndDate;

    @Element(name = "Sequence")
    private int mSequence;

    @Element(name = "PlayDay")
    private String mPlayDay;

    @Element(name = "OverrideTime")
    private String mOverrideTime;

    @ElementList(name = "Assets")
    private List<Asset> mAssets;

    public long getmCampaignID() {
        return mCampaignID;
    }

    public void setmCampaignID(long mCampaignID) {
        this.mCampaignID = mCampaignID;
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

    public int getmSequence() {
        return mSequence;
    }

    public void setmSequence(int mSequence) {
        this.mSequence = mSequence;
    }

    public String getmPlayDay() {
        return mPlayDay;
    }

    public void setmPlayDay(String mPlayDay) {
        this.mPlayDay = mPlayDay;
    }

    public String getmOverrideTime() {
        return mOverrideTime;
    }

    public void setmOverrideTime(String mOverrideTime) {
        this.mOverrideTime = mOverrideTime;
    }

    public List<Asset> getmAssets() {
        return mAssets;
    }

    public void setmAssets(List<Asset> mAssets) {
        this.mAssets = mAssets;
    }
}
