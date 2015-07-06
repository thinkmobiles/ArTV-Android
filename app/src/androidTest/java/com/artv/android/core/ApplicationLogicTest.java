package com.artv.android.core;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.ReflectionHelper;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.system.SpHelper;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class ApplicationLogicTest {

    private ApplicationLogic mApplicationLogic;

    @Before
    public final void init() {
        mApplicationLogic = new ApplicationLogic(InstrumentationRegistry.getTargetContext());
    }

    @After
    public final void DeInit() throws NoSuchFieldException, IllegalAccessException {
        ((SpHelper) ReflectionHelper.getField(mApplicationLogic, "mSpHelper")).clearAll();
    }

    @Test
    public final void Create_FieldsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Assert.assertNotNull(mApplicationLogic.getApiWorker());
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mSpHelper"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mConfigInfoWorker"));
    }

    @Test
    public final void Create_StateAppStart() {
        Assert.assertEquals(mApplicationLogic.getArTvState(), ArTvState.STATE_APP_START);
    }

    @Test
    public final void Create_SaveAndDeleteConfigInfo_StateMatch() throws NoSuchFieldException, IllegalAccessException {
        final ConfigInfo ci1 = new ConfigInfo();
        ci1.setDeviceId("id");
        ci1.setMasterDeviceIp("ip");
        ci1.setUser("user");
        ci1.setPassword("password");

        mApplicationLogic.getConfigInfoWorker().setConfigInfo(ci1);
        mApplicationLogic.getConfigInfoWorker().saveConfigInfo();

        Assert.assertEquals(new ApplicationLogic(InstrumentationRegistry.getTargetContext()).getArTvState(),
                ArTvState.STATE_APP_START_WITH_CONFIG_INFO);

        ((SpHelper) ReflectionHelper.getField(mApplicationLogic, "mSpHelper")).clearAll();

        Assert.assertEquals(new ApplicationLogic(InstrumentationRegistry.getTargetContext()).getArTvState(),
                ArTvState.STATE_APP_START);
    }

}
