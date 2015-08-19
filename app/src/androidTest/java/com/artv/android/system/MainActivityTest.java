package com.artv.android.system;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.artv.android.R;
import com.artv.android.core.state.ArTvState;
import com.artv.android.system.fragments.ConfigInfoFragment;

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
        Assert.assertEquals(ArTvState.STATE_APP_START, mMainActivity.getApplicationLogic().getStateWorker().getArTvState());

        final ConfigInfoFragment fragment = (ConfigInfoFragment) mMainActivity
                .getSupportFragmentManager()
                .findFragmentById(R.id.flFragmentContainer_AM);
    }

//    @Ignore
//    @Test
//    public final void ActivityStart_StateAppStartWithConfigInfo_SplashScreenFragmentIsShown() {
//        final ConfigInfo ci = new ConfigInfo.Builder()
//                .setDeviceId("id")
//                .setMasterDeviceIp("ip")
//                .setUser("user")
//                .setPassword("password")
//                .build();
//
//        mMainActivity.getApplicationLogic().getConfigInfoWorker().notifyEnteredConfigInfo(ci);
//        Assert.assertEquals(ArTvState.STATE_APP_START_WITH_CONFIG_INFO, mMainActivity.getApplicationLogic().getStateWorker().getArTvState());
//
//        mMainActivity.finish();
//        setActivity(null);
//        mMainActivity = getActivity();
//
//        Fragment fragment = (SplashScreenFragment) mMainActivity
//                .getFragmentManager()
//                .findFragmentById(R.id.flFragmentContainer_AM);
//
//        mMainActivity.getApplicationLogic().getConfigInfoWorker().removeConfigInfo();
//    }

//    @Ignore
//    @Test
//    public final void SetAndRemoveConfigInfo_FragmentsChangeProperly() throws Throwable {
//        final ConfigInfo ci = new ConfigInfo.Builder()
//                .setDeviceId("id")
//                .setMasterDeviceIp("ip")
//                .setUser("user")
//                .setPassword("password")
//                .build();
//
//        mMainActivity.getApplicationLogic().getConfigInfoWorker().notifyEnteredConfigInfo(ci);
//        runTestOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mMainActivity.getFragmentManager().executePendingTransactions();
//            }
//        });
//
//        Fragment fragment = (SplashScreenFragment) mMainActivity
//                .getFragmentManager()
//                .findFragmentById(R.id.flFragmentContainer_AM);
//
//        runTestOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mMainActivity.getApplicationLogic().getConfigInfoWorker().notifyNeedRemoveConfigInfo();
//                mMainActivity.getFragmentManager().executePendingTransactions();
//            }
//        });
//
//        fragment = (ConfigInfoFragment) mMainActivity
//                .getFragmentManager()
//                .findFragmentById(R.id.flFragmentContainer_AM);
//    }

}
