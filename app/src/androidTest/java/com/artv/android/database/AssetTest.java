package com.artv.android.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Asset;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.artv.android.database.DbTestHelper.buildAsset1;
import static com.artv.android.database.DbTestHelper.buildAsset2;
import static com.artv.android.database.DbTestHelper.buildAsset3;

/**
 * Created by ZOG on 8/17/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class AssetTest {

    private DbManager dbManager;

    @Before
    public void initializeDBManager() {
        dbManager = DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @After
    public void dropDB() {
        dbManager.dropDatabase();
    }

    @Test
    public final void WriteAssets_AssetsWrote_DatabaseContainsAssets() {
        final Asset asset1 = buildAsset1();
        final Asset asset2 = buildAsset2();
        final Asset asset3 = buildAsset3();

        Assert.assertFalse(-1 == dbManager.write(asset1));
        Assert.assertFalse(-1 == dbManager.write(asset2));
        Assert.assertFalse(-1 == dbManager.write(asset3));

        Assert.assertTrue(dbManager.contains(asset1));
        Assert.assertTrue(dbManager.contains(asset2));
        Assert.assertTrue(dbManager.contains(asset3));
    }

    @Test
    public final void WriteAssets_WriteSameAssetsAgain_AssetsReplaced() {
        final Asset asset1 = buildAsset1();
        final Asset asset2 = buildAsset2();
        final Asset asset3 = buildAsset3();

        Assert.assertTrue(dbManager.write(asset1) == dbManager.write(asset1));
        Assert.assertTrue(dbManager.write(asset2) == dbManager.write(asset2));
        Assert.assertTrue(dbManager.write(asset3) == dbManager.write(asset3));

        dbManager.write(asset1);
        dbManager.write(asset2);
        dbManager.write(asset3);

        final List<Asset> assets = dbManager.getAllAssets();
        Assert.assertTrue(assets.size() == 3);

        Assert.assertTrue(assets.contains(asset1));
        Assert.assertTrue(assets.contains(asset2));
        Assert.assertTrue(assets.contains(asset3));
    }

}
