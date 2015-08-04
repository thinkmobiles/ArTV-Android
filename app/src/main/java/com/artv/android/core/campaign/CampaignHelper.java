package com.artv.android.core.campaign;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 7/28/2015.
 */
public abstract class CampaignHelper {

    public static final int getCampaignsCount(final List<Campaign> _campaigns) {
        return _campaigns.size();
    }

    public static final int getAssetsCount(final List<Campaign> _campaigns) {
        int i = 0;
        for (final Campaign campaign : _campaigns) {
            i += campaign.assets.size();
        }
        return i;
    }

    public static final List<Asset> getAssetsToDownload(final List<Campaign> _campaigns) {
        final List<Asset> assets = new ArrayList<>();
        for (final Campaign campaign : _campaigns) {
            assets.addAll(campaign.assets);
        }
        return assets;
    }

}
