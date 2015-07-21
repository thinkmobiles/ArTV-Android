package com.artv.android.database;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.gen.DBAsset;
import com.artv.android.database.gen.DBCampaign;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Misha on 7/16/2015.
 */
public final class Transformer {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM");
    private Transformer() {}

    protected static List<DBCampaign> createDBCompaignList(List<Campaign> campaigns) {
        ArrayList<DBCampaign> dbCampaigns = new ArrayList<>(campaigns.size());

        for (Campaign campaign : campaigns)
            dbCampaigns.add(createDBCompaign(campaign));

        return dbCampaigns;
    }

    protected static List<Campaign> createCompaignList(List<DBCampaign> dbCampaigns) {
        ArrayList<Campaign> campaigns = new ArrayList<>(dbCampaigns.size());

        for (DBCampaign dbCampaign : dbCampaigns)
            campaigns.add(createCampaign(dbCampaign));

        return campaigns;
    }

    protected static DBCampaign createDBCompaign(Campaign campaign) {
        DBCampaign dbCampaign = new DBCampaign();
        dbCampaign.setId(campaign.getmCampaignID());
        dbCampaign.setCrcVersion(campaign.getmCRCVersion());
        dbCampaign.setStartDate(getMilisecFromStringDate(campaign.getmStartDate()));
        dbCampaign.setEndDate(getMilisecFromStringDate(campaign.getmEndDate()));
        dbCampaign.setPlayDay(campaign.getmPlayDay());
        dbCampaign.setOverrideTime(campaign.getmOverrideTime());
        dbCampaign.setSequence(campaign.getmSequence());

        return dbCampaign;
    }

    protected static Campaign createCampaign(DBCampaign dbCampaign) {
        Campaign campaign = new Campaign();
        campaign.setmCampaignID(dbCampaign.getId());
        campaign.setmCRCVersion(dbCampaign.getCrcVersion());
        campaign.setmStartDate(simpleDateFormat.format(new Date(dbCampaign.getStartDate())));
        campaign.setmEndDate(simpleDateFormat.format(new Date(dbCampaign.getEndDate())));
        campaign.setmOverrideTime(dbCampaign.getOverrideTime());
        campaign.setmSequence(dbCampaign.getSequence());
        campaign.setmPlayDay(dbCampaign.getPlayDay());
        campaign.setmAssets(createAssetsLis(dbCampaign.getAssets()));

        return campaign;
    }

    protected static List<DBAsset> createDBAssetsLis(List<Asset> assetList, Long compaignId) {
        ArrayList<DBAsset> dbAssets = new ArrayList<>(assetList.size());

        DBAsset a;
        for (Asset asset : assetList) {
            a = createDBAsset(asset);
            a.setCampaignId(compaignId);
            dbAssets.add(a);
        }

        return dbAssets;
    }

    protected static List<Asset> createAssetsLis(List<DBAsset> dbAssetsList) {
        ArrayList<Asset> assets = new ArrayList<>(dbAssetsList.size());

        for (DBAsset dbAsset : dbAssetsList)
            assets.add(createAsset(dbAsset));

        return assets;
    }

    private static DBAsset createDBAsset(Asset asset) {
        DBAsset dbAsset = new DBAsset();
        dbAsset.setSequence(asset.getmSequence());
        dbAsset.setDuration(asset.getmDuration());
        dbAsset.setName(asset.getmName());
        dbAsset.setUrl(asset.getmURL());

        return dbAsset;
    }

    private static Asset createAsset(DBAsset dbAsset) {
        Asset asset = new Asset();
        asset.setmSequence(dbAsset.getSequence());
        asset.setmDuration(dbAsset.getDuration());
        asset.setmName(dbAsset.getName());
        asset.setmURL(dbAsset.getUrl());

        return asset;
    }

    public static long getMilisecFromStringDate(String date) {
        return simpleDateFormat.parse(date, new ParsePosition(0)).getTime();
    }
}
