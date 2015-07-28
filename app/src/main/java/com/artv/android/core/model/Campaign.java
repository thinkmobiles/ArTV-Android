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

}
