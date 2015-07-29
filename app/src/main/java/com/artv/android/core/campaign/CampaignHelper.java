package com.artv.android.core.campaign;

import com.artv.android.core.model.Campaign;

import java.util.List;

/**
 * Created by ZOG on 7/28/2015.
 */
abstract class CampaignHelper {

    protected static final int getCampaignsCount(final List<Campaign> _campaigns) {
        return _campaigns.size();
    }

    protected static final int getAssetsCount(final List<Campaign> _campaigns) {
        int i = 0;
        for (final Campaign campaign : _campaigns) {
            i += campaign.assets.size();
        }
        return i;
    }

}
