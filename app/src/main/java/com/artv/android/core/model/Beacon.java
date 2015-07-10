package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

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
    public Asset currentAsset;

    @ElementList(name = "Campaigns")
    public ArrayList<Campaign> campaigns;

    @Element(name = "MsgBoardCampaign")
    public MsgBoardCampaign msgBoardCampaign;

    @Element(name = "ErrorLog")
    public String errorLog;

}
