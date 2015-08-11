package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 7/10/2015.
 */
@Root(name = "BeaconXML")
public final class Beacon {

    @Element(name = "TagID")
    public String tagId;

    @Element(name = "CurrentDateTime")
    public String currentDateTime;

    @Element(name = "CurrentCampaign")
    public int currentCampaign;

    @Element(name = "CurrentAsset")
    public int currentAsset;

    @ElementList(name = "Campaigns")
    public List<Campaign> campaigns;

    @Element(name = "MsgBoardCampaign", required = false)
    public MsgBoardCampaign msgBoardCampaign;

    @Element(name = "ErrorLog")
    public String errorLog = "";

}
