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
}
