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

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        Assert.assertNotNull(mApplicationLogic.getApiWorker());
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mSpHelper"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mConfigInfoWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mStateChangeListeners"));
    }

    @Test
    public final void IArTvStateChangeListeners_AddAndRemoveSuccessfully() {
        final IArTvStateChangeListener l1 = new IArTvStateChangeListener() {
            @Override
            public final void onArTvStateChanged() {

            }
        };
        final IArTvStateChangeListener l2 = new IArTvStateChangeListener() {
            @Override
            public final void onArTvStateChanged() {

            }
        };

        Assert.assertTrue(mApplicationLogic.addStateChangeListener(l1));
        Assert.assertTrue(mApplicationLogic.addStateChangeListener(l2));
        Assert.assertFalse(mApplicationLogic.addStateChangeListener(l2));
        Assert.assertFalse(mApplicationLogic.addStateChangeListener(l2));
        Assert.assertTrue(mApplicationLogic.removeStateChangeListener(l1));
        Assert.assertTrue(mApplicationLogic.removeStateChangeListener(l2));
        Assert.assertFalse(mApplicationLogic.removeStateChangeListener(l1));
        Assert.assertFalse(mApplicationLogic.removeStateChangeListener(l2));
    }

    @Test
    public final void IArTvStateChangeListener_Notifying() throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final IArTvStateChangeListener l1 = new IArTvStateChangeListener() {
            @Override
            public final void onArTvStateChanged() {
                countDownLatch.countDown();
            }
        };
        mApplicationLogic.addStateChangeListener(l1);
        ReflectionHelper.invoke(mApplicationLogic, "notifyStateChangeListeners");
        final boolean interrupted = !countDownLatch.await(1, TimeUnit.SECONDS);
        Assert.assertFalse(interrupted);
    }

    @Test
    public final void Create_StateAppStart() {
        Assert.assertEquals(mApplicationLogic.getArTvState(), ArTvState.STATE_APP_START);
    }

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

        Assert.assertEquals(new ApplicationLogic(InstrumentationRegistry.getTargetContext()).getArTvState(),
                ArTvState.STATE_APP_START_WITH_CONFIG_INFO);

        mApplicationLogic.getConfigInfoWorker().removeConfigInfo();

        Assert.assertEquals(new ApplicationLogic(InstrumentationRegistry.getTargetContext()).getArTvState(),
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

        mApplicationLogic.onEnteredConfigInfo(ci1);
        mApplicationLogic.getConfigInfoWorker().loadConfigInfo();

        Assert.assertEquals(mApplicationLogic.getConfigInfoWorker().getConfigInfo(), ci1);
        Assert.assertEquals(mApplicationLogic.getArTvState(), ArTvState.STATE_APP_START_WITH_CONFIG_INFO);

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

        mApplicationLogic.onEnteredConfigInfo(ci1);
        mApplicationLogic.onNeedRemoveConfigInfo();

        mApplicationLogic.getConfigInfoWorker().loadConfigInfo();
        Assert.assertFalse(mApplicationLogic.getConfigInfoWorker().getConfigInfo().hasConfigInfo());
        Assert.assertEquals(mApplicationLogic.getArTvState(), ArTvState.STATE_APP_START);
    }
}
