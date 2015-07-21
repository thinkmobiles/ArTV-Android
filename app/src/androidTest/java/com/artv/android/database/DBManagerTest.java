package com.artv.android.database;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

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
public class DBManagerTest {

    DBManager dbManager;
    static List<Campaign> testCampaigns;

    @BeforeClass
    public static void init() {
        testCampaigns = new ArrayList<>(2);

        Campaign campaign1 = new Campaign();
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

        Campaign campaign2 = new Campaign();
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
    public void testDbManagerReadAndWrite() {
        Assert.assertNotNull("Test campaigns is null", testCampaigns);

        Assert.assertTrue("Error while writing campaign", dbManager.addNewOrUpdateCampaigns(testCampaigns));

        List<Campaign> resCampaigns = dbManager.getAllCampaigns();

        Assert.assertNotNull("Result from getAllCampaigns is null", resCampaigns);

        for (int i=0; i<resCampaigns.size(); i++) {
            Assert.assertEquals("Campaigns not equal", testCampaigns.get(i), resCampaigns.get(i));
        }

    }

    @Test
    public void getCampaignByIdTest() {
        Campaign resCampaign = dbManager.getCampaignById((long) 23);

        Assert.assertNotNull("Result Campaign is null", resCampaign);

        Assert.assertEquals("Campaigns not equal", testCampaigns.get(0), resCampaign);
    }

    @Test
    public void getCampaignsFromDateTest() {

    }




}
