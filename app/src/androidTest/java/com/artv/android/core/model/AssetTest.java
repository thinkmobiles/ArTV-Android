package com.artv.android.core.model;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Misha on 8/6/2015.
 */
@RunWith(AndroidJUnit4.class)
public class AssetTest {

    @Test
    public void Asset_Equal() {
        Asset asset1 = createTestAsset();
        Asset asset2 = createTestAsset();

        Assert.assertEquals("Assets are not equal",asset1,asset1);
        Assert.assertEquals("Assets are not equal",asset1,asset2);
    }

    public Asset createTestAsset() {
        Asset asset1 = new Asset();
        asset1.url = "url1";
        asset1.name = null;
        asset1.sequence = 12345;
        asset1.duration = 1232;

        return asset1;
    }

    @Test
    public final void IdIsUnique() {
        final Asset asset1 = createTestAsset();
        final Asset asset2 = createTestAsset();

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

        Assert.assertTrue(asset3.hashCode() != asset4.hashCode());
    }

}
