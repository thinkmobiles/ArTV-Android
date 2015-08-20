package com.artv.android.core.model;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Misha on 8/6/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class AssetTest {

    @Test
    public final void Asset_Equal() {
        final Asset asset1 = createAsset1();
        final Asset asset1copy = createAsset1();
        final Asset asset2 = createAsset2();

        Assert.assertEquals("Assets are not equal", asset1, asset1);
        Assert.assertEquals("Assets are not equal", asset1, asset1copy);
        Assert.assertFalse(asset1.equals(asset2));
    }

    private final Asset createAsset1() {
        final Asset asset = new Asset();
        asset.url = "url1";
        asset.name = null;
        asset.sequence = 12345;
        asset.duration = 1232;
        return asset;
    }

    private final Asset createAsset2() {
        final Asset asset = new Asset();
        asset.url = "url1";
        asset.name = "asset2";
        asset.sequence = 878943245;
        asset.duration = 12389672;
        return asset;
    }

    @Test
    public final void IdIsUnique() {
        final Asset asset1 = createAsset1();
        final Asset asset2 = createAsset1();

        Assert.assertEquals(asset1.getAssetId(), asset2.getAssetId());

        final Asset asset3 = new Asset();
        asset3.name = "fsdf";
        asset3.url = "fsdf";
        asset3.duration = 10;
        asset3.sequence = 12;

        final Asset asset4 = new Asset();
        asset4.name = "fsdf";
        asset4.url = "fsdf";
        asset4.duration = 11;
        asset4.sequence = 12;

        Assert.assertTrue(asset3.getAssetId() != asset4.getAssetId());
    }

}
