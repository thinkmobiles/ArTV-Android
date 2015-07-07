package com.artv.android.system;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.artv.android.R;
import com.artv.android.core.ArTvState;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.system.fragments.ConfigInfoFragment;
import com.artv.android.system.fragments.SplashScreenFragment;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 7/7/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mMainActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
//        ReflectionHelper.setField(MyApplication.class, "mApplicationLogic", new ApplicationLogic(InstrumentationRegistry.getTargetContext()));
        mMainActivity = getActivity();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public final void CheckPreconditions() {
        Assert.assertNotNull(mMainActivity);
        Assert.assertNotNull(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public final void ActivityStart_StateAppStart_ConfigsFragmentIsShown() {
        Assert.assertEquals(ArTvState.STATE_APP_START, mMainActivity.getMyApplication().getApplicationLogic().getArTvState());

        final ConfigInfoFragment fragment = (ConfigInfoFragment) mMainActivity
                .getFragmentManager()
                .findFragmentById(R.id.flFragmentContainer_AM);
    }

    @Test
    public final void SetConfigInfo_StateAppStartWithConfigInfo_SplashScreenFragmentIsShown() throws Throwable {
        final ConfigInfo ci = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        mMainActivity.getMyApplication().getApplicationLogic().onEnteredConfigInfo(ci);
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMainActivity.getFragmentManager().executePendingTransactions();
            }
        });

        final SplashScreenFragment fragment = (SplashScreenFragment) mMainActivity
                .getFragmentManager()
                .findFragmentById(R.id.flFragmentContainer_AM);

        mMainActivity.getMyApplication().getApplicationLogic().getConfigInfoListener().onNeedRemoveConfigInfo();
    }
}
