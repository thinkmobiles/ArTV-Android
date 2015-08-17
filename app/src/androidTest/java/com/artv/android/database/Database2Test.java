package com.artv.android.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.gen.DaoSession;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZOG on 8/17/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class Database2Test {

    private DbManager2 dbManager;

    @Before
    public void initializeDBManager() {
        dbManager = DbManager2.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @After
    public void dropDB() {
        dbManager.dropDatabase();
    }

    @Test
    public final void WriteAssets_AssetsWrote_DatabaseContainsAssets() {
        final Asset asset1 = buildAsset1();
        final Asset asset2 = buildAsset2();
        final Asset asset3 = buildAsset3();

        Assert.assertFalse(-1 == dbManager.write(asset1));
        Assert.assertFalse(-1 == dbManager.write(asset2));
        Assert.assertFalse(-1 == dbManager.write(asset3));

        Assert.assertTrue(dbManager.contains(asset1));
        Assert.assertTrue(dbManager.contains(asset2));
        Assert.assertTrue(dbManager.contains(asset3));
    }

    @Test
    public final void WriteAssets_WriteSameAssetsAgain_AssetsReplaced() {
        final Asset asset1 = buildAsset1();
        final Asset asset2 = buildAsset2();
        final Asset asset3 = buildAsset3();

        Assert.assertTrue(dbManager.write(asset1) == dbManager.write(asset1));
        Assert.assertTrue(dbManager.write(asset2) == dbManager.write(asset2));
        Assert.assertTrue(dbManager.write(asset3) == dbManager.write(asset3));

        dbManager.write(asset1);
        dbManager.write(asset2);
        dbManager.write(asset3);

        final List<Asset> assets = dbManager.getAllAssets();
        Assert.assertTrue(assets.size() == 3);

        Assert.assertTrue(assets.contains(asset1));
        Assert.assertTrue(assets.contains(asset2));
        Assert.assertTrue(assets.contains(asset3));
    }

    @Test
    public final void WriteCampaigns_CampaignsWrote_DatabaseContainsCampaigns() {
        final Campaign campaign1 = buildCampaign1();
        final Campaign campaign2 = buildCampaign2();
        final Campaign campaign3 = buildCampaign3();

        Assert.assertFalse(-1 == dbManager.write(campaign1));
        Assert.assertFalse(-1 == dbManager.write(campaign2));
        Assert.assertFalse(-1 == dbManager.write(campaign3));

        Assert.assertTrue(dbManager.contains(campaign1));
        Assert.assertTrue(dbManager.contains(campaign2));
        Assert.assertTrue(dbManager.contains(campaign3));
    }

    @Test
    public final void WriteCampaigns_WriteSameCampaignsAgain_CampaignsReplaced() {
        final Campaign campaign1 = buildCampaign1();
        final Campaign campaign2 = buildCampaign2();
        final Campaign campaign3 = buildCampaign3();

        Assert.assertTrue(dbManager.write(campaign1) == dbManager.write(campaign1));
        Assert.assertTrue(dbManager.write(campaign2) == dbManager.write(campaign2));
        Assert.assertTrue(dbManager.write(campaign3) == dbManager.write(campaign3));

        dbManager.write(campaign1);
        dbManager.write(campaign2);
        dbManager.write(campaign3);

        final List<Campaign> campaigns = dbManager.getAllCampaigns();
        Assert.assertTrue(campaigns.size() == 3);

        Assert.assertTrue(campaigns.contains(campaign1));
        Assert.assertTrue(campaigns.contains(campaign2));
        Assert.assertTrue(campaigns.contains(campaign3));
    }

    @Test
    public final void WriteCampaign_GetAssets_AssetsMatch() {
        final Campaign campaign1 = buildCampaign1();
        campaign1.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());

        for(int i = 0; i < campaign1.assets.size(); i++) dbManager.write(campaign1.assets.get(i));

        dbManager.write(campaign1);

        final List<Asset> loadedAssets = dbManager.getAssets(campaign1);

        Assert.assertTrue(campaign1.assets.size() == loadedAssets.size());
        for (final Asset asset : campaign1.assets) {
            Assert.assertTrue(loadedAssets.contains(asset));
        }
    }

    @Test
    public final void WriteCampaignMultipleTimes_GetAssets_AssetsMatchAndNotDuplicate() {
        final Campaign campaign1 = buildCampaign1();
        campaign1.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());

        for(int i = 0; i < campaign1.assets.size(); i++) dbManager.write(campaign1.assets.get(i));

        dbManager.write(campaign1);
        dbManager.write(campaign1);
        dbManager.write(campaign1);
        dbManager.write(campaign1);

        final List<Asset> loadedAssets = dbManager.getAssets(campaign1);

        Assert.assertTrue(campaign1.assets.size() == loadedAssets.size());
        for (final Asset asset : campaign1.assets) {
            Assert.assertTrue(loadedAssets.contains(asset));
        }
    }


    private final Asset buildAsset1() {
        final Asset asset1 = new Asset();
        asset1.name = "asset1";
        asset1.url = "asset1/asset1";
        asset1.duration = 1;
        asset1.sequence = 1;
        return asset1;
    }

    private final Asset buildAsset2() {
        final Asset asset2 = new Asset();
        asset2.name = "asset2";
        asset2.url = "asset2/asset2";
        asset2.duration = 2;
        asset2.sequence = 2;
        return asset2;
    }

    private final Asset buildAsset3() {
        final Asset asset3 = new Asset();
        asset3.name = "asset3";
        asset3.url = "asset3/asset3";
        asset3.duration = 3;
        asset3.sequence = 3;
        return asset3;
    }

    private final Campaign buildCampaign1() {
        final Campaign campaign1 = new Campaign();
        campaign1.campaignId = 1;
        campaign1.crcVersion = "1";
        campaign1.startDate = "2015-01-01";
        campaign1.endDate = "2015-01-10";
        campaign1.overrideTime = "01:00 PM";
        campaign1.sequence = 1;
        campaign1.playDay = "1000000";
        campaign1.assets = new ArrayList<>();
        return campaign1;
    }

    private final Campaign buildCampaign2() {
        final Campaign campaign2 = new Campaign();
        campaign2.campaignId = 2;
        campaign2.crcVersion = "2";
        campaign2.startDate = "2015-02-02";
        campaign2.endDate = "2015-02-20";
        campaign2.overrideTime = "02:00 PM";
        campaign2.sequence = 2;
        campaign2.playDay = "0100000";
        campaign2.assets = new ArrayList<>();
        return campaign2;
    }

    private final Campaign buildCampaign3() {
        final Campaign campaign3 = new Campaign();
        campaign3.campaignId = 3;
        campaign3.crcVersion = "3";
        campaign3.startDate = "2015-03-03";
        campaign3.endDate = "2015-03-30";
        campaign3.overrideTime = "03:00 PM";
        campaign3.sequence = 3;
        campaign3.playDay = "0010000";
        campaign3.assets = new ArrayList<>();
        return campaign3;
    }

}
