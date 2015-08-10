package com.artv.android.core.model;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Misha on 8/6/2015.
 */
@RunWith(AndroidJUnit4.class)
public class CampaignTest {
    @Test
    public void Campaign_Equal() {
        Campaign campaign1 = createTestCampaign();
        Campaign campaign2 = createTestCampaign();

        Assert.assertEquals("Campaigns are not equal", campaign1, campaign1);
        Assert.assertEquals("Campaigns are not equal", campaign1, campaign2);
    }

    private Campaign createTestCampaign() {
        Campaign campaign1 = new Campaign();
        campaign1.campaignId = 23;
        campaign1.sequence = 1234;
        campaign1.playDay = "playday1";
        campaign1.overrideTime = "over1";
        campaign1.crcVersion = "12";
        campaign1.endDate = null;
        campaign1.startDate = "2015-03-07";

        List<Asset> assets1 = new LinkedList<>();

        Asset asset1 = new Asset();
        asset1.url = "url1";
        asset1.name = null;
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

        return campaign1;
    }
}
