package com.artv.android.database;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;

import java.util.List;

/**
 * Created by ZOG on 8/5/2015.
 */
public interface DbWorker {

    void write(final Asset _asset);
    boolean contains(final Asset _asset);
    List<Asset> getAllAssets();

    void write(final Campaign _campaign);
    boolean contains(final Campaign _campaign);
    List<Campaign> getAllCampaigns();
    List<Asset> getAssets(final Campaign _campaign);

    void write(final MsgBoardCampaign _msgBoardCampaign);
    MsgBoardCampaign getMsgBoardCampaign();
}
