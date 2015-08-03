package com.artv.android.core;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.ReflectionHelper;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.state.ArTvState;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
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

    @Test
    public final void Create_FieldsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mSpHelper"));
        Assert.assertNotNull(mApplicationLogic.getStateWorker());
        Assert.assertNotNull(mApplicationLogic.getConfigInfoWorker());
        Assert.assertNotNull(mApplicationLogic.getApiWorker());
        Assert.assertNotNull(mApplicationLogic.getInitWorker());
    }

    @Test
    public final void Create_StateAppStart() {
        Assert.assertEquals(mApplicationLogic.getStateWorker().getArTvState(), ArTvState.STATE_APP_START);
    }

    @Ignore
    @Test
    public final void Create_SaveAndRemoveConfigInfo_StateMatch() throws NoSuchFieldException, IllegalAccessException {
        final ConfigInfo ci1 = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        mApplicationLogic.getConfigInfoWorker().setConfigInfo(ci1);
        mApplicationLogic.getConfigInfoWorker().saveConfigInfo();

        Assert.assertEquals(new ApplicationLogic(InstrumentationRegistry.getTargetContext()).getStateWorker().getArTvState(),
                ArTvState.STATE_APP_START_WITH_CONFIG_INFO);

        mApplicationLogic.getConfigInfoWorker().removeConfigInfo();

        Assert.assertEquals(new ApplicationLogic(InstrumentationRegistry.getTargetContext()).getStateWorker().getArTvState(),
                ArTvState.STATE_APP_START);
    }

    @Test
    public final void IConfigInfoListener_InfoEntered_SavedAndStateMatch() {
        final ConfigInfo ci1 = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        mApplicationLogic.getConfigInfoWorker().notifyEnteredConfigInfo(ci1);
        mApplicationLogic.getConfigInfoWorker().loadConfigInfo();

        Assert.assertEquals(mApplicationLogic.getConfigInfoWorker().getConfigInfo(), ci1);
        Assert.assertEquals(mApplicationLogic.getStateWorker().getArTvState(), ArTvState.STATE_APP_START);

        mApplicationLogic.getConfigInfoWorker().removeConfigInfo();
    }

    @Test
    public final void IConfigInfoListenerEntered_NeedRemove_RemovedAndStateMatch() {
        final ConfigInfo ci1 = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        mApplicationLogic.getConfigInfoWorker().notifyEnteredConfigInfo(ci1);
        mApplicationLogic.getConfigInfoWorker().notifyNeedRemoveConfigInfo();

        mApplicationLogic.getConfigInfoWorker().loadConfigInfo();
        Assert.assertFalse(mApplicationLogic.getConfigInfoWorker().getConfigInfo().hasConfigInfo());
        Assert.assertEquals(mApplicationLogic.getStateWorker().getArTvState(), ArTvState.STATE_APP_START);
    }
}
