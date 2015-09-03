package com.artv.android.database;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;

import java.util.List;

/**
 * Created by ZOG on 8/5/2015.
 */
public interface DbWorker {

    long write(final Asset _asset);
    boolean contains(final Asset _asset);
    List<Asset> getAllAssets();

    long write(final Campaign _campaign);
    boolean contains(final Campaign _campaign);
    List<Campaign> getAllCampaigns();
    Campaign getCampaignById(final int _campaignId);

    long write(final MsgBoardCampaign _msgBoardCampaign);
    MsgBoardCampaign getMsgBoardCampaign();

    void drop();
}
