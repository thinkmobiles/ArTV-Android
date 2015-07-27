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
        msgBoardCampaign1.setmMsgBoardID(343);
        msgBoardCampaign1.setmRightBkgURL("rightb1");
        msgBoardCampaign1.setmBottomBkgURL("bottomb1");
        msgBoardCampaign1.setmPlayDay("playday1");
        msgBoardCampaign1.setmCRCVersion(343);
        msgBoardCampaign1.setmEndDate("2015-05-07");
        msgBoardCampaign1.setmStartDate("2015-03-07");
        msgBoardCampaign1.setmTextColor("txtclr1");

        List<Message> messages1 = new ArrayList<>(2);

        Message message1 = new Message();
        message1.setmText("text1");
        message1.setmPosition("pos1");
        message1.setmSequenceL(24);

        Message message2 = new Message();
        message2.setmText("text2");
        message2.setmPosition("pos2");
        message2.setmSequenceL(24);

        msgBoardCampaign1.setmMessages(messages1);

        msgBoardCampaign2 = new MsgBoardCampaign();
        msgBoardCampaign2.setmMsgBoardID(344);
        msgBoardCampaign2.setmRightBkgURL("rightb2");
        msgBoardCampaign2.setmBottomBkgURL("bottomb2");
        msgBoardCampaign2.setmPlayDay("playday2");
        msgBoardCampaign2.setmCRCVersion(3433);
        msgBoardCampaign2.setmEndDate("2015-09-07");
        msgBoardCampaign2.setmStartDate("2015-02-07");
        msgBoardCampaign2.setmTextColor("txtclr2");

        List<Message> messages2 = new ArrayList<>(2);

        Message message3 = new Message();
        message3.setmText("text3");
        message3.setmPosition("pos3");
        message3.setmSequenceL(245);

        Message message4 = new Message();
        message4.setmText("text4");
        message4.setmPosition("pos4");
        message4.setmSequenceL(244);

        msgBoardCampaign2.setmMessages(messages2);

        testMsgBoardCampaigns.add(msgBoardCampaign1);
        testMsgBoardCampaigns.add(msgBoardCampaign2);
    }

    private static void initCampaigns() {
        testCampaigns = new ArrayList<>(2);

        campaign1 = new Campaign();
        campaign1.setmCampaignID(23);
        campaign1.setmSequence(1234);
        campaign1.setmPlayDay("playday1");
        campaign1.setmOverrideTime("over1");
        campaign1.setmCRCVersion(12);
        campaign1.setmEndDate("2015-05-07");
        campaign1.setmStartDate("2015-03-07");

        List<Asset> assets1 = new LinkedList<>();

        Asset asset1 = new Asset();
        asset1.setmURL("url1");
        asset1.setmName("name1");
        asset1.setmSequence(12345);
        asset1.setmDuration(1232);

        Asset asset2 = new Asset();
        asset2.setmURL("url2");
        asset2.setmName("name2");
        asset2.setmSequence(123456);
        asset2.setmDuration(12322);

        assets1.add(asset1);
        assets1.add(asset2);

        campaign1.setmAssets(assets1);

        campaign2 = new Campaign();
        campaign2.setmCampaignID(24);
        campaign2.setmSequence(12345);
        campaign2.setmPlayDay("playday2");
        campaign2.setmOverrideTime("over2");
        campaign2.setmCRCVersion(13);
        campaign2.setmEndDate("2015-20-07");
        campaign2.setmStartDate("2015-01-07");


        List<Asset> assets2 = new LinkedList<>();

        Asset asset3 = new Asset();
        asset3.setmURL("url3");
        asset3.setmName("name3");
        asset3.setmSequence(123453);
        asset3.setmDuration(12323);

        Asset asset4 = new Asset();
        asset4.setmURL("url4");
        asset4.setmName("name4");
        asset4.setmSequence(1234564);
        asset4.setmDuration(123224);

        assets2.add(asset3);
        assets2.add(asset4);

        campaign2.setmAssets(assets2);

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
    public void DataBase_AddNewOrUpdateCampaignsAndGetAllCampaigns_WriteReadMatch() {
        Assert.assertTrue("Error while writing campaign", dbManager.addNewOrUpdateCampaigns(testCampaigns));

        List<Campaign> resCampaigns = dbManager.getAllCampaigns();

        Assert.assertNotSame("Result size is 0", 0, resCampaigns.size());

        for (int i=0; i<resCampaigns.size(); i++) {
            Assert.assertEquals("Campaigns not equal", testCampaigns.get(i), resCampaigns.get(i));
        }
    }

    @Test
    public void DataBase_GetCampaignById_WriteReadMatch() {
        Campaign resCampaign = dbManager.getCampaignById(campaign1.getmCampaignID());

        Assert.assertNotNull("Result Campaign is null", resCampaign);

        Assert.assertEquals("Campaigns not equal", campaign1, resCampaign);
    }


    @Test
    public void DataBase_GetCampaignsFromDate_ReadMatch() {
        long targetStartDate = Transformer.getMillisecFromStringDate("2015-03-07");
        List<Campaign> resCampaigns = dbManager.getCampaignsFromDate(targetStartDate);

        Assert.assertNotSame("Result size is 0", 0, resCampaigns.size());

        for (Campaign campaign : resCampaigns)
            Assert.assertTrue("Wrong result campaign",
                    Transformer.getMillisecFromStringDate(campaign.getmStartDate()) >= targetStartDate);

    }

    @Test
    public void DataBase_AddNewOrUpdateMsgBoardCampaignsAndGetAllMsgBoardCampaigns_WriteReadMatch() {
        Assert.assertTrue("Error while writing campaign", dbManager.addNewOrUpdateMsgBoardCampaigns(testMsgBoardCampaigns));

        List<MsgBoardCampaign> resMsgBoardCampaigns = dbManager.getAllMsgBoardCampaigns();

        Assert.assertNotSame("Result size is 0", 0, resMsgBoardCampaigns.size());

        for (int i=0; i<resMsgBoardCampaigns.size(); i++) {
            Assert.assertEquals("Campaigns not equal", testMsgBoardCampaigns.get(i), resMsgBoardCampaigns.get(i));
        }
    }

    @Test
    public void DataBase_GetMsgBoardCampaignById_WriteReadMatch() {
        MsgBoardCampaign resMsgBoardCampaign = dbManager.getMsgBoardCampaignById(msgBoardCampaign1.getmMsgBoardID());

        Assert.assertNotNull("Result msgBoardCampaign is null", resMsgBoardCampaign);

        Assert.assertEquals("MsgBoardCampaigns not equal \n res "+resMsgBoardCampaign.toString(), msgBoardCampaign1, resMsgBoardCampaign);
    }

    @Test
    public void DataBase_GetMsgBoardCampaignsFromDate_ReadMatch() {
        long targetStartDate = Transformer.getMillisecFromStringDate(msgBoardCampaign2.getmStartDate());
        List<MsgBoardCampaign> resMsgBoardCampaigns = dbManager.getMsgBoardCampaignsFromDate(targetStartDate);

        Assert.assertNotSame("Result size is 0", 0, resMsgBoardCampaigns.size());

        for (MsgBoardCampaign campaign : resMsgBoardCampaigns)
            Assert.assertTrue("Wrong result msgBoardCampaign",
                    Transformer.getMillisecFromStringDate(campaign.getmStartDate()) >= targetStartDate);

    }
}
