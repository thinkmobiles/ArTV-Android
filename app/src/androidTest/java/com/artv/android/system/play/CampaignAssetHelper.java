package com.artv.android.system.play;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import java.util.ArrayList;

/**
 * Created by mRogach on 21.08.2015.
 */
abstract class CampaignAssetHelper {

    protected static final Asset buildAsset1() {
        final Asset asset1 = new Asset();
        asset1.name = "asset1";
        asset1.url = "asset1/asset1";
        asset1.duration = 3;
        asset1.sequence = 1;
        return asset1;
    }

    protected static final Asset buildAsset2() {
        final Asset asset2 = new Asset();
        asset2.name = "asset2";
        asset2.url = "asset2/asset2";
        asset2.duration = 2;
        asset2.sequence = 2;
        return asset2;
    }

    protected static final Asset buildAsset3() {
        final Asset asset3 = new Asset();
        asset3.name = "asset3";
        asset3.url = "asset3/asset3";
        asset3.duration = 3;
        asset3.sequence = 3;
        return asset3;
    }

    protected static final Asset buildAsset4() {
        final Asset asset1 = new Asset();
        asset1.name = "asset4";
        asset1.url = "asset4/asset4";
        asset1.duration = 4;
        asset1.sequence = 1;
        return asset1;
    }

    protected static final Asset buildAsset5() {
        final Asset asset2 = new Asset();
        asset2.name = "asset5";
        asset2.url = "asset5/asset5";
        asset2.duration = 2;
        asset2.sequence = 2;
        return asset2;
    }

    protected static final Asset buildAsset6() {
        final Asset asset3 = new Asset();
        asset3.name = "asset6";
        asset3.url = "asset6/asset6";
        asset3.duration = 3;
        asset3.sequence = 3;
        return asset3;
    }

    protected static final Asset buildAsset7() {
        final Asset asset1 = new Asset();
        asset1.name = "asset7";
        asset1.url = "asset7/asset7";
        asset1.duration = 4;
        asset1.sequence = 1;
        return asset1;
    }

    protected static final Asset buildAsset8() {
        final Asset asset2 = new Asset();
        asset2.name = "asset8";
        asset2.url = "asset8/asset8";
        asset2.duration = 2;
        asset2.sequence = 2;
        return asset2;
    }

    protected static final Asset buildAsset9() {
        final Asset asset3 = new Asset();
        asset3.name = "asset9";
        asset3.url = "asset9/asset9";
        asset3.duration = 3;
        asset3.sequence = 3;
        return asset3;
    }

    protected static final Campaign buildCampaign1() {
        final Campaign campaign1 = new Campaign();
        campaign1.campaignId = 1;
        campaign1.crcVersion = "1";
        campaign1.startDate = "2015-01-01";
        campaign1.endDate = "2015-01-10";
        campaign1.overrideTime = "01:00 PM";
        campaign1.sequence = 0;
        campaign1.playDay = "1000000";
        campaign1.assets = new ArrayList<>();
        return campaign1;
    }

    protected static final Campaign buildCampaign2() {
        final Campaign campaign2 = new Campaign();
        campaign2.campaignId = 2;
        campaign2.crcVersion = "2";
        campaign2.startDate = "2015-02-02";
        campaign2.endDate = "2015-02-20";
        campaign2.overrideTime = "02:00 PM";
        campaign2.sequence = 1;
        campaign2.playDay = "0100000";
        campaign2.assets = new ArrayList<>();
        return campaign2;
    }

    protected static final Campaign buildCampaign3() {
        final Campaign campaign3 = new Campaign();
        campaign3.campaignId = 3;
        campaign3.crcVersion = "3";
        campaign3.startDate = "2015-03-03";
        campaign3.endDate = "2015-03-30";
        campaign3.overrideTime = "03:00 PM";
        campaign3.sequence = 2;
        campaign3.playDay = "0010000";
        campaign3.assets = new ArrayList<>();
        return campaign3;
    }
}
