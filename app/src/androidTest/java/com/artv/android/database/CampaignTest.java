package com.artv.android.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.artv.android.database.DbTestHelper.buildAsset1;
import static com.artv.android.database.DbTestHelper.buildAsset2;
import static com.artv.android.database.DbTestHelper.buildAsset3;
import static com.artv.android.database.DbTestHelper.buildCampaign1;
import static com.artv.android.database.DbTestHelper.buildCampaign2;
import static com.artv.android.database.DbTestHelper.buildCampaign3;

/**
 * Created by ZOG on 8/18/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class CampaignTest {

    private DbManager dbManager;

    @Before
    public void initializeDBManager() {
        dbManager = DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @After
    public void dropDB() {
        dbManager.dropDatabase();
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

    @Test
    public final void WriteFewCampaignWithSameAssets_GetAssets_AssetsMatch() {
        final Campaign campaign1 = buildCampaign1();
        campaign1.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());
        for(int i = 0; i < campaign1.assets.size(); i++) dbManager.write(campaign1.assets.get(i));
        dbManager.write(campaign1);

        final Campaign campaign2 = buildCampaign2();
        campaign2.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());
        for(int i = 0; i < campaign2.assets.size(); i++) dbManager.write(campaign2.assets.get(i));
        dbManager.write(campaign2);

        final List<Asset> loadedAssets1 = dbManager.getAssets(campaign1);
        final List<Asset> loadedAssets2 = dbManager.getAssets(campaign2);

        Assert.assertTrue(campaign1.assets.size() == loadedAssets1.size());
        for (final Asset asset : campaign1.assets) {
            Assert.assertTrue(loadedAssets1.contains(asset));
        }

        Assert.assertTrue(campaign2.assets.size() == loadedAssets2.size());
        for (final Asset asset : campaign2.assets) {
            Assert.assertTrue(loadedAssets2.contains(asset));
        }
    }

    @Test
    public final void WriteFewCampaignWithOverlappingAssets_GetAssets_AssetsMatch() {
        final Campaign campaign1 = buildCampaign1();
        campaign1.assets = Arrays.asList(buildAsset1(), buildAsset3());
        for(int i = 0; i < campaign1.assets.size(); i++) dbManager.write(campaign1.assets.get(i));
        dbManager.write(campaign1);

        final Campaign campaign2 = buildCampaign2();
        campaign2.assets = Arrays.asList(buildAsset2(), buildAsset3());
        for(int i = 0; i < campaign2.assets.size(); i++) dbManager.write(campaign2.assets.get(i));
        dbManager.write(campaign2);

        final List<Asset> loadedAssets1 = dbManager.getAssets(campaign1);
        final List<Asset> loadedAssets2 = dbManager.getAssets(campaign2);

        Assert.assertTrue(campaign1.assets.size() == loadedAssets1.size());
        for (final Asset asset : campaign1.assets) {
            Assert.assertTrue(loadedAssets1.contains(asset));
        }

        Assert.assertTrue(campaign2.assets.size() == loadedAssets2.size());
        for (final Asset asset : campaign2.assets) {
            Assert.assertTrue(loadedAssets2.contains(asset));
        }
    }

    @Test
    public final void WriteCampaignWithMoreAssets_GetAssets_AssetsMatch() {
        final Campaign campaign = buildCampaign1();

        campaign.assets = Arrays.asList(buildAsset1(), buildAsset2());
        for(int i = 0; i < campaign.assets.size(); i++) dbManager.write(campaign.assets.get(i));
        dbManager.write(campaign);

        campaign.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());
        for(int i = 0; i < campaign.assets.size(); i++) dbManager.write(campaign.assets.get(i));
        dbManager.write(campaign);

        final List<Asset> loadedAssets = dbManager.getAssets(campaign);

        Assert.assertTrue(campaign.assets.size() == loadedAssets.size());
        for (final Asset asset : campaign.assets) {
            Assert.assertTrue(loadedAssets.contains(asset));
        }
    }

    @Test
    public final void WriteCampaignWithLessAssets_GetAssets_AssetsMatch() {
        final Campaign campaign = buildCampaign1();

        campaign.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());
        for(int i = 0; i < campaign.assets.size(); i++) dbManager.write(campaign.assets.get(i));
        dbManager.write(campaign);

        campaign.assets = Arrays.asList(buildAsset1(), buildAsset2());
        for(int i = 0; i < campaign.assets.size(); i++) dbManager.write(campaign.assets.get(i));
        dbManager.write(campaign);

        final List<Asset> loadedAssets = dbManager.getAssets(campaign);

        Assert.assertTrue(campaign.assets.size() == loadedAssets.size());
        for (final Asset asset : campaign.assets) {
            Assert.assertTrue(loadedAssets.contains(asset));
        }
    }

    @Test
    public final void WriteCampaignWithAssets_WriteCampaignWithoutAssets_NoAssetsInCampaign() {
        final Campaign campaign = buildCampaign1();
        campaign.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());
        for(int i = 0; i < campaign.assets.size(); i++) dbManager.write(campaign.assets.get(i));
        dbManager.write(campaign);

        campaign.assets = new ArrayList<>();
        dbManager.write(campaign);

        final List<Asset> loadedAssets = dbManager.getAssets(campaign);
        Assert.assertTrue(loadedAssets.isEmpty());
    }

    @Test
    public final void WriteCampaignWithAssets_WriteCampaignWithoutAssets_UnrelatedAssetsSaved() {
        final Campaign campaign = buildCampaign1();
        campaign.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());
        for(int i = 0; i < campaign.assets.size(); i++) dbManager.write(campaign.assets.get(i));
        dbManager.write(campaign);

        campaign.assets = new ArrayList<>();
        dbManager.write(campaign);

        Assert.assertTrue(dbManager.getAllAssets().size() == 3);
    }

    @Test
    public final void GenerateId_IdUnique() {
        Assert.assertFalse(dbManager.generateId(5, 6) == dbManager.generateId(6, 5));
        Assert.assertFalse(dbManager.generateId(0, 1) == dbManager.generateId(1, 0));
        Assert.assertFalse(dbManager.generateId(1, 1) == dbManager.generateId(1, 0));
        Assert.assertFalse(dbManager.generateId(34, 5345) == dbManager.generateId(5345, 53245));
        Assert.assertTrue(dbManager.generateId(434, 434) == dbManager.generateId(434, 434));
        Assert.assertFalse(dbManager.generateId(1, 1527506766) == dbManager.generateId(2, 1331024045));
        Assert.assertFalse(dbManager.generateId(1, 1331024045) == dbManager.generateId(2, 1331024045));
    }

    @Test
    public final void WriteCampaign_GetCampaignById() {
        final Campaign campaign1 = buildCampaign1();
        final Campaign campaign2 = buildCampaign2();

        dbManager.write(campaign1);
        dbManager.write(campaign2);

        Assert.assertEquals(campaign1, dbManager.getCampaignById(1));
        Assert.assertEquals(campaign2, dbManager.getCampaignById(2));
    }

}
