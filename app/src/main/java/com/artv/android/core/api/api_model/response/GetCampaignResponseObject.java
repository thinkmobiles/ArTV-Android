package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * Rogach on 30.06.2015.
 */

@Root(name = "CampaignInfoXML")
public class GetCampaignResponseObject extends BaseResponseObject {

    @Element(name = "ErrorNumber", required = false)
    public int errorNumber;

    @Element(name = "ErrorDescription", required = false)
    public String errorDescription;

    @ElementList(name = "Campaigns", required = false)
    private ArrayList<Campaign> mCampaigns;

    @ElementList(name = "MessageBoardCampaigns", required = false)
    private List<MsgBoardCampaign> mMessageBoardCampaigns;

    public final ArrayList<Campaign> getCampaigns() {
        return mCampaigns;
    }

    public final MsgBoardCampaign getMsgBoardCampaign() {
        if (mMessageBoardCampaigns == null || mMessageBoardCampaigns.isEmpty()) return null;
        return mMessageBoardCampaigns.get(0);
    }

    public GetCampaignResponseObject() {
        apiType = ApiType.GET_CAMPAIGN;
    }
}
