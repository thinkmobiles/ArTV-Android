package com.artv.android.system.play;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.artv.android.system.play.CampaignAssetHelper.buildAsset1;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset2;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset3;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset4;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset5;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset6;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset7;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset8;
import static com.artv.android.system.play.CampaignAssetHelper.buildAsset9;
import static com.artv.android.system.play.CampaignAssetHelper.buildCampaign1;
import static com.artv.android.system.play.CampaignAssetHelper.buildCampaign2;
import static com.artv.android.system.play.CampaignAssetHelper.buildCampaign3;

/**
 * Created by mRogach on 21.08.2015.
 */
@RunWith(AndroidJUnit4.class)
public final class AssetsStackTest {

    private PlaybackWorker mPlaybackWorker;

    @Before
    public void initializeDBManager() {
        mPlaybackWorker = new PlaybackWorker();
    }

    @Test
    public final void WriteCampaigns_CampaignsWrote_SortPosition() {
        final List<Campaign> campaigns = new ArrayList<>();
        final Campaign campaign1 = buildCampaign1();
        final Campaign campaign2 = buildCampaign2();
        final Campaign campaign3 = buildCampaign3();
        campaigns.add(campaign2);
        campaigns.add(campaign1);
        campaigns.add(campaign3);
        mPlaybackWorker.sortCampaigns(campaigns);
        Assert.assertEquals(campaign3.sequence, campaigns.get(0).sequence);
        Assert.assertEquals(campaign2.sequence, campaigns.get(1).sequence);
        Assert.assertEquals(campaign1.sequence, campaigns.get(2).sequence);
    }

    @Test
    public final void WriteAssets_AssetsWrote_SortPosition() {
        final List<Asset> assets = new ArrayList<>();
        final Asset asset1 = buildAsset1();
        final Asset asset2 = buildAsset2();
        final Asset asset3 = buildAsset3();
        assets.add(asset1);
        assets.add(asset3);
        assets.add(asset2);
        mPlaybackWorker.sortAssets(assets);
        Assert.assertEquals(asset3.sequence, assets.get(0).sequence);
        Assert.assertEquals(asset2.sequence, assets.get(1).sequence);
        Assert.assertEquals(asset1.sequence, assets.get(2).sequence);
    }

    @Test
    public final void WriteStack_SortPosition() {
        final Asset asset1 = buildAsset1();
        final Asset asset2 = buildAsset2();
        final Asset asset3 = buildAsset3();
        final Asset asset4 = buildAsset4();
        final Asset asset5 = buildAsset5();
        final Asset asset6 = buildAsset6();
        final Asset asset7 = buildAsset7();
        final Asset asset8 = buildAsset8();
        final Asset asset9 = buildAsset9();
        final List<Asset> assets1 = new ArrayList<>();
        final List<Asset> assets2 = new ArrayList<>();
        final List<Asset> assets3 = new ArrayList<>();
        assets1.add(asset2);
        assets1.add(asset1);
        assets1.add(asset3);
        assets2.add(asset6);
        assets2.add(asset4);
        assets2.add(asset5);
        assets3.add(asset7);
        assets3.add(asset9);
        assets3.add(asset8);
        final Campaign campaign2 = buildCampaign2();
        final Campaign campaign1 = buildCampaign1();
        final Campaign campaign3 = buildCampaign3();
        campaign1.assets = assets1;
        campaign2.assets = assets2;
        campaign3.assets = assets3;
        final List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(campaign2);
        campaigns.add(campaign1);
        campaigns.add(campaign3);
//        final Stack<Asset> assetStack = mPlaybackWorker.getStackAssetsAllCampaigns(campaigns);

//        Assert.assertEquals(asset1.sequence, assetStack.pop().sequence);

    }
}
