package com.artv.android.database;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import java.util.List;

/**
 * Created by ZOG on 8/5/2015.
 */
public interface DbWorker {

    boolean contains(final Campaign _campaign);
    boolean contains(final Asset _asset);
    void write(final Campaign _campaign);
    void write(final Asset _asset);

    List<Campaign> getAllCampaigns();
    List<Asset> getAllAssets();

    List<Asset> getAssets(final Campaign _campaign);

}
