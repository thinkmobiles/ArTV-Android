package com.artv.android.core.beacon;

import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.DbWorker;

import java.util.List;

/**
 * Created by ZOG on 10/1/2015.
 */
public final class BeaconResultProcessor {

    private DbWorker mDbWorker;

    public final void processMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        mDbWorker.write(_msgBoardCampaign);
    }

    public final void processCampaigns(final List<Integer> _deletedCampaigns, final List<Campaign> _campaigns) {
        if (!_deletedCampaigns.isEmpty()) {
            //todo: delete campaigns by id
        }

        if (!_campaigns.isEmpty()) {
            //todo: load new campaigns
        }
    }

}
