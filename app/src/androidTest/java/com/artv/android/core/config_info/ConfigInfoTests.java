package com.artv.android.core.config_info;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.system.SpHelper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 7/6/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class ConfigInfoTests {

    private ConfigInfoWorker mCiWorker;
    private SpHelper mSpHelper;

    @Before
    public final void Init() {
        mCiWorker = new ConfigInfoWorker();
    }

    @Test
    public final void SaveAndLoadConfigInfo_ObjectsEquals() {
        mSpHelper = new SpHelper(InstrumentationRegistry.getTargetContext());
        mCiWorker.setSpHelper(mSpHelper);

        final ConfigInfo ci1 = new ConfigInfo();
        ci1.setDeviceId("id");
        ci1.setMasterDeviceIp("ip");
        ci1.setUser("user");
        ci1.setPassword("password");

        mCiWorker.setConfigInfo(ci1);
        mCiWorker.saveConfigInfo();
        mCiWorker.loadConfigInfo();

        Assert.assertEquals(ci1, mCiWorker.getConfigInfo());

        mSpHelper.clearAll();
    }

    @Test
    public final void ConfigInfoObject_HasConfigInfo_TrueOnlyIfAllDataFilled() {
        final ConfigInfo ci1 = new ConfigInfo();
        Assert.assertFalse(ci1.hasConfigInfo());
        ci1.setDeviceId("id");
        Assert.assertFalse(ci1.hasConfigInfo());
        ci1.setMasterDeviceIp("ip");
        Assert.assertFalse(ci1.hasConfigInfo());
        ci1.setUser("user");
        Assert.assertFalse(ci1.hasConfigInfo());
        ci1.setPassword("password");
        Assert.assertTrue(ci1.hasConfigInfo());
    }

    @Test
    public final void ConfigInfoProviders_AddAndRemove() {
        final IConfigInfoListener cip1 = new IConfigInfoListener() {
            @Override
            public final void onEnteredConfigInfo(final ConfigInfo _configInfo) {

            }
        };
        final IConfigInfoListener cip2 = new IConfigInfoListener() {
            @Override
            public final void onEnteredConfigInfo(final ConfigInfo _configInfo) {

            }
        };

        Assert.assertTrue(mCiWorker.addConfigInfoProvider(cip1));
        Assert.assertTrue(mCiWorker.addConfigInfoProvider(cip2));
        Assert.assertFalse(mCiWorker.addConfigInfoProvider(cip1));
        Assert.assertFalse(mCiWorker.addConfigInfoProvider(cip2));
        Assert.assertTrue(mCiWorker.removeConfigInfoProvider(cip1));
        Assert.assertTrue(mCiWorker.removeConfigInfoProvider(cip2));
        Assert.assertFalse(mCiWorker.removeConfigInfoProvider(cip1));
        Assert.assertFalse(mCiWorker.removeConfigInfoProvider(cip2));
    }

}
