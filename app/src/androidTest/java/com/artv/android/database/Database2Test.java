package com.artv.android.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    @Test
    public final void WriteMsgBoardCampaign_MsgBoardCampaignWrote_DatabaseContainsMsgBoardCampaign() {
        final MsgBoardCampaign msg1 = buildMsgBoardCampaign1();
        final MsgBoardCampaign msg2 = buildMsgBoardCampaign2();

        Assert.assertFalse(-1 == dbManager.write(msg1));
        Assert.assertFalse(-1 == dbManager.write(msg2));
    }

    @Test
    public final void WriteMsgBoardCampaign_WriteSameMsgBoardCampaignAgain_MsgBoardCampaignReplaced() {
        final MsgBoardCampaign msg1 = buildMsgBoardCampaign1();
        final MsgBoardCampaign msg2 = buildMsgBoardCampaign2();

        Assert.assertTrue(dbManager.write(msg1) == dbManager.write(msg1));
        Assert.assertTrue(dbManager.write(msg2) == dbManager.write(msg2));
    }

    @Test
    public final void WriteMsgBoardCampaign_OnlyLastMsgBoardCampaignStored() {
        final MsgBoardCampaign msg1 = buildMsgBoardCampaign1();
        final MsgBoardCampaign msg2 = buildMsgBoardCampaign2();

        dbManager.write(msg1);
        Assert.assertEquals(dbManager.getMsgBoardCampaign(), msg1);
        dbManager.write(msg2);
        Assert.assertEquals(dbManager.getMsgBoardCampaign(), msg2);
    }

    @Test
    public final void NoMsgBoardCampaign_GetMsgBoardCampaign_ReturnsNull() {
        Assert.assertNull(dbManager.getMsgBoardCampaign());
    }

    @Test
    public final void WriteMsgBoardCampaignWithMessages_GetMsgBoardCampaign_MessagesMatch() {

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

    private final MsgBoardCampaign buildMsgBoardCampaign1() {
        final MsgBoardCampaign msg = new MsgBoardCampaign();
        msg.msgBoardId = 1;
        msg.crcVersion = "1";
        msg.startDate = "2015-01-01";
        msg.endDate = "2015-01-10";
        msg.playDay = "1000000";
        msg.textColor = "red";
        msg.rightBkgURL = "rightBkgURL";
        msg.bottomBkgURL = "bottomBkgURL";
        msg.messages = new ArrayList<>();
        return msg;
    }

    private final MsgBoardCampaign buildMsgBoardCampaign2() {
        final MsgBoardCampaign msg = new MsgBoardCampaign();
        msg.msgBoardId = 2;
        msg.crcVersion = "2";
        msg.startDate = "2015-02-02";
        msg.endDate = "2015-02-20";
        msg.playDay = "0100000";
        msg.textColor = "green";
        msg.rightBkgURL = "rightBkgURL";
        msg.bottomBkgURL = "bottomBkgURL";
        msg.messages = new ArrayList<>();
        return msg;
    }

    protected final Message buildMessage(final int _seed) {
        final Message message = new Message();
        message.position = _seed + "";
        message.sequence = _seed;
        message.text = _seed + "";
        return message;
    }

}
