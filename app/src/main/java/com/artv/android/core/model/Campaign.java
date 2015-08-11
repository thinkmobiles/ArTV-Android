package com.artv.android.core.model;

import android.text.TextUtils;

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

    @Element(name = "CampaignID", required = false)
    public int campaignId;

    @Element(name = "CRCVersion", required = false)
    public String crcVersion;

    @Element(name = "StartDate", required = false)
    public String startDate;

    @Element(name = "EndDate", required = false)
    public String endDate;

    @Element(name = "Sequence", required = false)
    public int sequence;

    @Element(name = "PlayDay", required = false)
    public String playDay;

    @Element(name = "OverrideTime", required = false)
    public String overrideTime;

    @ElementList(name = "Assets", required = false)
    public List<Asset> assets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;
        if(campaignId != campaign.campaignId) return false;
        if(crcVersion != campaign.crcVersion) return false;
        if(!TextUtils.equals(endDate,campaign.endDate)) return false;
        if(!TextUtils.equals(startDate,campaign.startDate)) return false;
        if(!TextUtils.equals(overrideTime,campaign.overrideTime)) return false;
        if(!TextUtils.equals(playDay,campaign.playDay)) return false;
        if(sequence != campaign.sequence) return false;

        for (int i=0; i < assets.size(); i++) {
            if(!assets.get(i).equals(campaign.assets.get(i))) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "mCampaignID=" + campaignId +
                ", mCRCVersion=" + crcVersion +
                ", mStartDate='" + startDate + '\'' +
                ", mEndDate='" + endDate + '\'' +
                ", mSequence=" + sequence +
                ", mPlayDay='" + playDay + '\'' +
                ", mOverrideTime='" + overrideTime + '\'' +
                ", mAssets=" + assets +
                '}';
    }
}
