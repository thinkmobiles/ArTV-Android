package com.artv.android.system.play;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Stack;

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

    @Test
    public final void WriteAssets_AssetsWrote_StackRightPosition() {

        final Campaign campaign1 = buildCampaign1();
        final Campaign campaign2 = buildCampaign2();
        final Campaign campaign3 = buildCampaign3();

        final Asset asset1 = buildAsset1();
        final Asset asset2 = buildAsset2();
        final Asset asset3 = buildAsset3();
        final Asset asset4 = buildAsset4();
        final Asset asset5 = buildAsset5();
        final Asset asset6 = buildAsset6();
        final Asset asset7 = buildAsset7();
        final Asset asset8 = buildAsset8();
        final Asset asset9 = buildAsset9();

        campaign1.assets.add(asset1);
        campaign1.assets.add(asset2);
        campaign1.assets.add(asset3);
        campaign2.assets.add(asset4);
        campaign2.assets.add(asset5);
        campaign2.assets.add(asset6);
        campaign3.assets.add(asset7);
        campaign3.assets.add(asset8);
        campaign3.assets.add(asset9);

        Stack<Asset> stack1 = new Stack<>();
        Stack<Asset> stack2 = new Stack<>();

        stack1.push(asset9);
        stack1.push(asset8);
        stack1.push(asset7);
        stack1.push(asset6);
        stack1.push(asset5);
        stack1.push(asset4);
        stack1.push(asset3);
        stack1.push(asset2);
        stack1.push(asset1);

    }
}
