package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by ZOG on 7/10/2015.
 *
 * Unused class. todo: delete CampaignInfo
 */
@Root(name = "CampaignInfoXML")
public final class CampaignInfo {

    @Element(name = "Campaigns")
    public List<Campaign> campaigns;

    @Element(name = "MsgBoardCampaign")
    public MsgBoardCampaign msgBoardCampaign;

}
