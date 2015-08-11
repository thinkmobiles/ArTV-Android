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
    public final boolean equals(final Object _o) {
        if (this == _o) return true;
        if (_o == null) return false;
        if (getClass() != _o.getClass()) return false;

        final Campaign campaign = (Campaign) _o;
        return campaignId == campaign.campaignId && String.valueOf(crcVersion).equals(String.valueOf(campaign.crcVersion));
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
