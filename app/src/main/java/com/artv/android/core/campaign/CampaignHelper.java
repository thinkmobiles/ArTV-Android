package com.artv.android.core.campaign;

import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import junit.framework.Assert;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

    public static final void removeInvalidItems(final List<Campaign> _campaigns) {
        final ArrayList<Campaign> campaigns = (ArrayList<Campaign>) _campaigns;
        final ArrayList<Campaign> campaignsToRemove = new ArrayList<>();

        for (final Campaign campaign : campaigns) {
            if (campaign.assets == null || campaign.assets.isEmpty()) {
                campaignsToRemove.add(campaign);
                continue;
            }

            final List<Asset> assetsToRemove = new ArrayList<>();

            for (final Asset asset : campaign.assets) {
                if (!asset.isValid()) assetsToRemove.add(asset);
            }

            for (final Asset asset : assetsToRemove) {
                ((ArrayList) campaign.assets).remove(asset);
                ArTvLogger.printMessage("Removed invalid asset: " + asset);
            }
        }

        for (final Campaign campaign : campaignsToRemove) {
            campaigns.remove(campaign);
            ArTvLogger.printMessage("Removed invalid campaign: " + campaign);
        }
    }

}
