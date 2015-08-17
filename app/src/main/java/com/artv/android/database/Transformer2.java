package com.artv.android.database;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.gen.DBAsset;
import com.artv.android.database.gen.DBCampaign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 8/17/2015.
 */
public final class Transformer2 {

    protected final DBAsset createDBAsset(final Asset _asset) {
        final DBAsset dbAsset = new DBAsset();
        dbAsset.setId((long) _asset.getAssetId());
        dbAsset.setSequence(_asset.sequence);
        dbAsset.setDuration(_asset.duration);
        dbAsset.setName(_asset.name);
        dbAsset.setUrl(_asset.url);
        return dbAsset;
    }

    private final Asset createAsset(final DBAsset _dbAsset) {
        final Asset asset = new Asset();
        asset.sequence = _dbAsset.getSequence();
        asset.duration = _dbAsset.getDuration();
        asset.name = _dbAsset.getName();
        asset.url = _dbAsset.getUrl();
        return asset;
    }

    protected final List<Asset> createAssetsList(final List<DBAsset> _dbAssets) {
        final List<Asset> assets = new ArrayList<>(_dbAssets.size());
        for (final DBAsset dbAsset : _dbAssets) assets.add(createAsset(dbAsset));
        return assets;
    }

    protected final DBCampaign createDBCampaign(final Campaign _campaign) {
        final DBCampaign dbCampaign = new DBCampaign();
        dbCampaign.setId((long) _campaign.campaignId);
        dbCampaign.setCrcVersion(_campaign.crcVersion);
        dbCampaign.setStartDate(_campaign.startDate);
        dbCampaign.setEndDate(_campaign.endDate);
        dbCampaign.setPlayDay(_campaign.playDay);
        dbCampaign.setOverrideTime(_campaign.overrideTime);
        dbCampaign.setSequence(_campaign.sequence);
        return dbCampaign;
    }

    private final Campaign createCampaign(final DBCampaign _dbCampaign) {
        final Campaign campaign = new Campaign();
        campaign.campaignId = _dbCampaign.getId().intValue();
        campaign.crcVersion = _dbCampaign.getCrcVersion();
        campaign.startDate = _dbCampaign.getStartDate();
        campaign.endDate = _dbCampaign.getEndDate();
        campaign.overrideTime = _dbCampaign.getOverrideTime();
        campaign.sequence = _dbCampaign.getSequence();
        campaign.playDay = _dbCampaign.getPlayDay();

        return campaign;
    }

    protected final List<Campaign> createCampaignList(final List<DBCampaign> _dbCampaigns) {
        final List<Campaign> campaigns = new ArrayList<>(_dbCampaigns.size());
        for (final DBCampaign dbCampaign : _dbCampaigns) campaigns.add(createCampaign(dbCampaign));
        return campaigns;
    }

}
