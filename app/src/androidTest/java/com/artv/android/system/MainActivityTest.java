package com.artv.android.system;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

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
        Assert.fail();
    }
}
