package com.artv.android.core.model;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by ZOG on 8/12/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class GlobalConfigTest {

    private GlobalConfig mGlobalConfig;

    @Before
    public final void init() {
        mGlobalConfig = new GlobalConfig();
    }

    @Test
    public final void BadDataFormat_RemovesRedundantQuotes() {
        mGlobalConfig.entries = new ArrayList<>();
        final Entry entry = new Entry();
        entry.setName("\"" + GlobalConfig.KEY_DEF_PLAY_TIME + "\"");
        entry.setValue("\"155\"");
        mGlobalConfig.entries.add(entry);
        mGlobalConfig.prepareEntries();

        Assert.assertTrue(GlobalConfig.KEY_DEF_PLAY_TIME.equals(mGlobalConfig.entries.get(0).getName()));
        Assert.assertTrue("155".equals(mGlobalConfig.entries.get(0).getValue()));
    }

    @Test
    public final void NoTimeInGonfig_ReturnDefaultTime() throws NoSuchFieldException, IllegalAccessException {
        mGlobalConfig.entries = new ArrayList<>();
        Assert.assertEquals(GlobalConfig.DEF_PLAY_TIME, mGlobalConfig.getServerDefaultPlayTime());
    }

    @Test
    public final void HasTimeInGonfig_ReturnThatTime() throws NoSuchFieldException, IllegalAccessException {
        mGlobalConfig.entries = new ArrayList<>();
        final Entry entry = new Entry();
        entry.setName(GlobalConfig.KEY_DEF_PLAY_TIME);
        entry.setValue("155");
        mGlobalConfig.entries.add(entry);
        Assert.assertEquals(155, mGlobalConfig.getServerDefaultPlayTime());
    }

}
