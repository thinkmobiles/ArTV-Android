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
    private long mCampaignID;

    @Element(name = "CRCVersion", required = false)
    private String mCRCVersion;

    @Element(name = "StartDate", required = false)
    private String mStartDate;

    @Element(name = "EndDate", required = false)
    private String mEndDate;

    @Element(name = "Sequence", required = false)
    private int mSequence;

    @Element(name = "PlayDay", required = false)
    private String mPlayDay;

    @Element(name = "OverrideTime", required = false)
    private String mOverrideTime;

    @ElementList(name = "Assets", required = false)
    private List<Asset> mAssets;

}
