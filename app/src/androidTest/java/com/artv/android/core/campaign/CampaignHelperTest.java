package com.artv.android.core.campaign;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static com.artv.android.ModelsTestHelper.buildAsset1;
import static com.artv.android.ModelsTestHelper.buildAsset2;
import static com.artv.android.ModelsTestHelper.buildAsset3;
import static com.artv.android.ModelsTestHelper.buildCampaign1;
import static com.artv.android.ModelsTestHelper.buildCampaign2;
import static com.artv.android.ModelsTestHelper.buildCampaign3;

/**
 * Created by ZOG on 9/2/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class CampaignHelperTest {

    @Test
    public final void GetCampaignsCount() {
        final Campaign campaign1 = buildCampaign1();
        final Campaign campaign2 = buildCampaign2();
        final Campaign campaign3 = buildCampaign3();
        Assert.assertEquals(2, CampaignHelper.getCampaignsCount(Arrays.asList(campaign1, campaign2)));
        Assert.assertEquals(3, CampaignHelper.getCampaignsCount(Arrays.asList(campaign1, campaign2, campaign3)));
    }

    @Test
    public final void GetAssetsCount() {
        final Campaign campaign1 = buildCampaign1();
        campaign1.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());
        final Campaign campaign2 = buildCampaign2();
        campaign2.assets = Arrays.asList(buildAsset1(), buildAsset2(), buildAsset3());

        Assert.assertEquals(0, CampaignHelper.getAssetsCount(Arrays.asList(buildCampaign1())));
        Assert.assertEquals(3, CampaignHelper.getAssetsCount(Arrays.asList(campaign1)));
        Assert.assertEquals(6, CampaignHelper.getAssetsCount(Arrays.asList(campaign1, campaign2)));
    }

    @Test
    public final void CreateCampaignsWithInvalidItems_InvalidItemsRemoved() {
        final Asset invalidAsset = buildAsset1();
        invalidAsset.name = null;
        invalidAsset.url = "";
        final Campaign invalidCampaign = buildCampaign1();

        final Campaign campaign2 = buildCampaign2();
        campaign2.assets = new ArrayList<>(Arrays.asList(invalidAsset, buildAsset2(), buildAsset3()));

        final ArrayList<Campaign> campaigns = new ArrayList<>(Arrays.asList(invalidCampaign, campaign2));

        CampaignHelper.removeInvalidItems(campaigns);

        Assert.assertTrue(!campaigns.contains(invalidCampaign));
        Assert.assertTrue(!campaigns.get(0).assets.contains(invalidAsset));
    }

}
