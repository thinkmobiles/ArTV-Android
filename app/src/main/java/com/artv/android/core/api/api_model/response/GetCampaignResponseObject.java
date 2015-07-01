package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by
 * Rogach on 30.06.2015.
 */

@Root(name = "CampaignInfoXML")
public final class GetCampaignResponseObject extends BaseResponseObject {

    @ElementList(name = "Campaigns")
    private List<Campaign> mCampaigns;

    @Element(name = "MsgBoardCampaign")
    private MsgBoardCampaign mMsgBoardCampaign;

    public GetCampaignResponseObject() {
        apiType = ApiType.GET_CAMPAIGN;
    }
}
