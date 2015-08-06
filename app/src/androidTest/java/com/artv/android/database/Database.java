package com.artv.android.database;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Misha on 7/20/2015.
 */
@RunWith(AndroidJUnit4.class)
public class Database {

    private DBManager dbManager;

    private static List<Campaign> testCampaigns;
    private static Campaign campaign1, campaign2;

    private static List<MsgBoardCampaign> testMsgBoardCampaigns;
    private static MsgBoardCampaign msgBoardCampaign1, msgBoardCampaign2;


    @BeforeClass
    public static void init() {
        initCampaigns();
        initMsgBoardCampaigns();
    }

    private static void initMsgBoardCampaigns() {
        testMsgBoardCampaigns = new ArrayList<>(2);

        msgBoardCampaign1 = new MsgBoardCampaign();
        msgBoardCampaign1.msgBoardId = 343;
        msgBoardCampaign1.rightBkgURL = "rightb1";
        msgBoardCampaign1.bottomBkgURL = "bottomb1";
        msgBoardCampaign1.playDay = "playday1";
        msgBoardCampaign1.crcVersion = "343";
        msgBoardCampaign1.endDate = "2015-05-07";
        msgBoardCampaign1.startDate = "2015-03-07";
        msgBoardCampaign1.textColor = "txtclr1";

        List<Message> messages1 = new ArrayList<>(2);

        Message message1 = new Message();
        message1.text = "text1";
        message1.position = "pos1";
        message1.sequence = 24;

        Message message2 = new Message();
        message2.text = "text2";
        message2.position = "pos2";
        message2.sequence = 24;

        msgBoardCampaign1.messages = (messages1);

        msgBoardCampaign2 = new MsgBoardCampaign();
        msgBoardCampaign2.msgBoardId = 344;
        msgBoardCampaign2.rightBkgURL = "rightb2";
        msgBoardCampaign2.bottomBkgURL = "bottomb2";
        msgBoardCampaign2.playDay = "playday2";
        msgBoardCampaign2.crcVersion = "3433";
        msgBoardCampaign2.endDate = "2015-09-07";
        msgBoardCampaign2.startDate = "2015-02-07";
        msgBoardCampaign2.textColor = "txtclr2";

        List<Message> messages2 = new ArrayList<>(2);

        Message message3 = new Message();
        message3.text = "text3";
        message3.position = "pos3";
        message3.sequence = 245;

        Message message4 = new Message();
        message4.text = "text4";
        message4.position = "pos4";
        message4.sequence = 244;

        msgBoardCampaign2.messages = messages2;

        testMsgBoardCampaigns.add(msgBoardCampaign1);
        testMsgBoardCampaigns.add(msgBoardCampaign2);
    }

    private static void initCampaigns() {
        testCampaigns = new ArrayList<>(2);

        campaign1 = new Campaign();
        campaign1.campaignId = 23;
        campaign1.sequence = 1234;
        campaign1.playDay = "playday1";
        campaign1.overrideTime = "over1";
        campaign1.crcVersion = "12";
        campaign1.endDate = "2015-05-07";
        campaign1.startDate = "2015-03-07";

        List<Asset> assets1 = new LinkedList<>();

        Asset asset1 = new Asset();
        asset1.url = "url1";
        asset1.name = "name1";
        asset1.sequence = 12345;
        asset1.duration = 1232;

        Asset asset2 = new Asset();
        asset2.url = "url2";
        asset2.name = "name2";
        asset2.sequence = 123456;
        asset2.duration = 12322;

        assets1.add(asset1);
        assets1.add(asset2);

        campaign1.assets = (assets1);

        campaign2 = new Campaign();
        campaign2.campaignId = 24;
        campaign2.sequence = 12345;
        campaign2.playDay = "playday2";
        campaign2.overrideTime = "over2";
        campaign2.crcVersion = "13";
        campaign2.endDate = "2015-20-07";
        campaign2.startDate = "2015-01-07";


        List<Asset> assets2 = new LinkedList<>();

        Asset asset3 = new Asset();
        asset3.url = "url3";
        asset3.name = "name3";
        asset3.sequence = 123453;
        asset3.duration = 12323;

        Asset asset4 = new Asset();
        asset4.url = "url4";
        asset4.name = "name4";
        asset4.sequence = 1234564;
        asset4.duration = 123224;

        assets2.add(asset3);
        assets2.add(asset4);

        campaign2.assets = assets2;

        testCampaigns.add(campaign1);
        testCampaigns.add(campaign2);
    }

    @AfterClass
    public static void dropDB() {
        DBManager dbManager = DBManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        dbManager.dropDatabase();
    }

    @Before
    public void initializeDBManager() {
        dbManager = DBManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void DataBase_WriteAndContains_Campaign() {
        dbManager.write(campaign1);
        Assert.assertTrue("Database not contain campaign !", dbManager.contains(campaign1));
    }

    @Test
    public void DataBase_WriteAndContains_Assert() {
        dbManager.write(campaign1.assets.get(0));
        Assert.assertTrue("Database not contain asset !", dbManager.contains(campaign1.assets.get(0)));
    }

    @Test
    public void DataBase_GetAssetsForCampaign() {
        List<Asset> resAssets = dbManager.getAssets(campaign1);

        Assert.assertSame("Wrong size of assets result list", campaign1.assets.size(), resAssets.size());

        for (int i=0; i<resAssets.size(); i++) {
            Assert.assertEquals("Asserts not equal", campaign1.assets.get(i), resAssets.get(i));
        }
    }

    @Test
    public void DataBase_AddNewOrUpdateCampaignsAndGetAllCampaigns_WriteReadMatch() {
//        Assert.assertTrue("Error while writing campaign", dbManager.addNewOrUpdateCampaigns(testCampaigns));
//
//        List<Campaign> resCampaigns = dbManager.getAllCampaigns();
//
//        Assert.assertNotSame("Result size is 0", 0, resCampaigns.size());
//
//        for (int i=0; i<resCampaigns.size(); i++) {
//            Assert.assertEquals("Campaigns not equal", testCampaigns.get(i), resCampaigns.get(i));
//        }
    }

    @Test
    public void DataBase_GetCampaignById_WriteReadMatch() {
//        Campaign resCampaign = dbManager.getCampaignById(campaign1.campaignId);
//
//        Assert.assertNotNull("Result Campaign is null", resCampaign);
//
//        Assert.assertEquals("Campaigns not equal", campaign1, resCampaign);
    }

    @Test
    public void DataBase_GetCampaignsFromDate_ReadMatch() {
//        long targetStartDate = Transformer.getMillisecFromStringDate("2015-03-07");
//        List<Campaign> resCampaigns = dbManager.getCampaignsFromDate(targetStartDate);
//
//        Assert.assertNotSame("Result size is 0", 0, resCampaigns.size());
//
//        for (Campaign campaign : resCampaigns)
//            Assert.assertTrue("Wrong result campaign",
//                    Transformer.getMillisecFromStringDate(campaign.startDate) >= targetStartDate);

    }

    @Test
    public void DataBase_AddNewOrUpdateMsgBoardCampaignsAndGetAllMsgBoardCampaigns_WriteReadMatch() {
//        Assert.assertTrue("Error while writing campaign", dbManager.addNewOrUpdateMsgBoardCampaigns(testMsgBoardCampaigns));
//
//        List<MsgBoardCampaign> resMsgBoardCampaigns = dbManager.getAllMsgBoardCampaigns();
//
//        Assert.assertNotSame("Result size is 0", 0, resMsgBoardCampaigns.size());
//
//        for (int i=0; i<resMsgBoardCampaigns.size(); i++) {
//            Assert.assertEquals("Campaigns not equal", testMsgBoardCampaigns.get(i), resMsgBoardCampaigns.get(i));
//        }
    }

    @Test
    public void DataBase_GetMsgBoardCampaignById_WriteReadMatch() {
//        MsgBoardCampaign resMsgBoardCampaign = dbManager.getMsgBoardCampaignById(msgBoardCampaign1.msgBoardId);
//
//        Assert.assertNotNull("Result msgBoardCampaign is null", resMsgBoardCampaign);
//
//        Assert.assertEquals("MsgBoardCampaigns not equal \n res "+resMsgBoardCampaign.toString(), msgBoardCampaign1, resMsgBoardCampaign);
    }

    @Test
    public void DataBase_GetMsgBoardCampaignsFromDate_ReadMatch() {
//        long targetStartDate = Transformer.getMillisecFromStringDate(msgBoardCampaign2.getmStartDate());
//        List<MsgBoardCampaign> resMsgBoardCampaigns = dbManager.getMsgBoardCampaignsFromDate(targetStartDate);
//
//        Assert.assertNotSame("Result size is 0", 0, resMsgBoardCampaigns.size());
//
//        for (MsgBoardCampaign campaign : resMsgBoardCampaigns)
//            Assert.assertTrue("Wrong result msgBoardCampaign",
//                    Transformer.getMillisecFromStringDate(campaign.getmStartDate()) >= targetStartDate);

    }
}
