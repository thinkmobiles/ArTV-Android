package com.artv.android.core.api.api_model.response;


import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 7/10/2015.
 */

public final class BeaconResponseObject extends BaseResponseObject {

    @Element(name = "ErrorNumber", required = false)
    public int errorNumber;

    @Element(name = "ErrorDescription", required = false)
    public String errorDescription;

    @ElementList(name = "Campaigns", required = false)
    public ArrayList<Campaign> campaigns;

    @Element(name = "MsgBoardCampaign", required = false)
    public MsgBoardCampaign msgBoardCampaign;

    @ElementList(name = "DeletedCampaigns", required = false)
    public List<Integer> deletedCampaigns;

    public BeaconResponseObject() {
        apiType = ApiType.BEACON;
    }

}
