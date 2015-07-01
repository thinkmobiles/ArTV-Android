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

}
