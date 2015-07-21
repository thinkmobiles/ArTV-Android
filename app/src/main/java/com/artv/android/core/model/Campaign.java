package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;
        if(mCampaignID != campaign.mCampaignID) return false;
        if(mCRCVersion != campaign.mCRCVersion) return false;
        if(!mEndDate.equals(campaign.mEndDate)) return false;
        if(!mStartDate.equals(campaign.mStartDate)) return false;
        if(!mOverrideTime.equals(campaign.mOverrideTime)) return false;
        if(!mPlayDay.equals(campaign.mPlayDay)) return false;
        if(mSequence != campaign.mSequence) return false;

        for (int i=0; i < mAssets.size(); i++) {
            if(!mAssets.get(i).equals(campaign.mAssets.get(i))) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "mCampaignID=" + mCampaignID +
                ", mCRCVersion=" + mCRCVersion +
                ", mStartDate='" + mStartDate + '\'' +
                ", mEndDate='" + mEndDate + '\'' +
                ", mSequence=" + mSequence +
                ", mPlayDay='" + mPlayDay + '\'' +
                ", mOverrideTime='" + mOverrideTime + '\'' +
                ", mAssets=" + mAssets +
                '}';
    }
}
