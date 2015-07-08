package com.artv.android.core.config_info;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.state.StateWorker;
import com.artv.android.system.SpHelper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 7/6/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class ConfigInfoWorkerTest {

    private ConfigInfoWorker mCiWorker;
    private SpHelper mSpHelper;

    @Before
    public final void Init() {
        mCiWorker = new ConfigInfoWorker();
        mSpHelper = new SpHelper(InstrumentationRegistry.getTargetContext());
        mCiWorker.setSpHelper(mSpHelper);
    }

    @Test
    public final void SaveAndLoadConfigInfo_ObjectsEquals() {
        final ConfigInfo ci1 = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        mCiWorker.setConfigInfo(ci1);
        mCiWorker.saveConfigInfo();
        mCiWorker.loadConfigInfo();

        Assert.assertEquals(ci1, mCiWorker.getConfigInfo());

        mCiWorker.removeConfigInfo();

        Assert.assertFalse(ci1.equals(mCiWorker.getConfigInfo()));
        mCiWorker.loadConfigInfo();
        Assert.assertFalse(mCiWorker.getConfigInfo().hasConfigInfo());
    }

}
